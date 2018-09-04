# -*- coding: utf-8 -*-
import pymysql
import xml.etree.ElementTree as ET
import datetime
import os
import importer.util as util

def do_import(db, base_dir):
    NEWS_DIR = os.path.join(base_dir, "news")

    ET.register_namespace('holarse', "http://holarse.de/entity/importer/")

    if not os.path.exists(NEWS_DIR):
        os.makedirs(NEWS_DIR)
    
    cur = db.cursor()
    cur.execute("select n.nid, n.title, n.created, n.changed, r.title, r.body, r.log, r.vid, n.status, n.comment from node n inner join node_revisions r on r.vid = n.vid where n.type = 'story'")

    counter = 0
    for a_result in cur.fetchall():
        counter = counter + 1
        uid = str(a_result[0])
        vid = str(a_result[7])

        filename = "%s.xml" % uid
        filepath = os.path.join(NEWS_DIR, filename)

        if os.path.exists(filepath):
            print("skipping %s" % filename)
            continue
        
        print("-----------------------------------------%s,%s" % (uid, vid))

        xml_news = ET.Element('{http://holarse.de/entity/importer/}news')
        xml_news.set('uid', uid)
        xml_news.set('revision', vid)

        # state
        state_tags = ET.SubElement(xml_news, 'state')
        xml_published = ET.SubElement(state_tags, 'published')
        xml_published.text = 'true' if a_result[8] == 1 else 'false'

        xml_commentable = ET.SubElement(state_tags, 'commentable')
        xml_commentable.text = 'true' if a_result[9] == 2 else 'false'

        # titles
        ET.SubElement(xml_news, 'title').text = a_result[4]

        # content
        xml_content = ET.SubElement(xml_news, 'content')
        xml_content.text = a_result[5]
        xml_content.set('type', 'WIKI')

        ET.SubElement(xml_news, 'created_at').text = util.ts_to_utc(a_result[2])
        if a_result[3]:
            ET.SubElement(xml_news, 'updated_at').text = util.ts_to_utc(a_result[3])

        # attachments
        att_tags = ET.SubElement(xml_news, 'attachments')

        # homepage
        cur_hp = db.cursor()
        cur_hp.execute("select field_homepage_value, delta from content_field_homepage where nid = %s and vid = %s" % (uid, vid))
        for l_result in cur_hp.fetchall():

            xml_hp = ET.SubElement(att_tags, 'attachment')
            xml_hp.text = l_result[0]
            xml_hp.set('type', 'LINK')
            xml_hp.set('prio', str(l_result[1]))

        # videos
        cur_videos = db.cursor()
        cur_videos.execute("select field_video_value from content_field_video where nid = %s and vid = %s", (uid, vid))
        for v_result in cur_videos.fetchall():
            # steam
            if v_result[0]:
                xml_video = ET.SubElement(att_tags, 'attachment')
                xml_video.text = v_result[0]
                xml_video.set('type', 'YOUTUBE')

        # screenshots
        cur_ss = db.cursor()
        cur_ss.execute("select filepath from content_field_screenshots cfs inner join files f on cfs.field_screenshots_fid = f.fid where nid = %s and vid = %s order by delta", (uid, vid))
        for ss_result in cur_ss.fetchall():
            xml_screenshot = ET.SubElement(att_tags, 'attachment')
            xml_screenshot.text = ss_result[0]
            xml_screenshot.set('type', 'IMAGE')

        # file attachments
        cur_fa = db.cursor()
        cur_fa.execute("select filepath from content_field_attachments cfs inner join files f on cfs.field_attachments_fid = f.fid where nid = %s and vid = %s order by delta", (uid, vid))
        for fa_result in cur_fa.fetchall():
            xml_fa = ET.SubElement(att_tags, 'attachment')
            xml_fa.text = fa_result[0]
            xml_fa.set('type', 'FILE')
            
        
        ET.ElementTree(xml_news).write(filepath, "UTF-8", True)
        #ET.dump(xml_news)

    return counter
