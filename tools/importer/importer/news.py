# -*- coding: utf-8 -*-
import MySQLdb
import xml.etree.ElementTree as ET
import datetime
import os
import importer.util as util

def do_import(db, base_dir):
    NEWS_DIR = os.path.join(base_dir, "news")

    if not os.path.exists(NEWS_DIR):
        os.makedirs(NEWS_DIR)
    
    cur = db.cursor()
    cur.execute("select n.nid, n.title, n.created, n.changed, r.title, r.body, r.log, r.vid from node n inner join node_revisions r on r.vid = n.vid where n.type = 'story'")

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
        
        xml_news = ET.Element('news')
        xml_news.set('uid', uid)
        xml_news.set('revision', vid)
    
        ET.SubElement(xml_news, 'title').text = a_result[4]
        xml_content = ET.SubElement(xml_news, 'content')
        xml_content.text = a_result[5]
        xml_content.set('content-type', 'wiki')
        ET.SubElement(xml_news, 'created_at').text = util.ts_to_utc(a_result[2])

        ET.ElementTree(xml_news).write(filepath, "UTF-8", True)
        ET.dump(xml_news)

    return counter
