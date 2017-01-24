# -*- coding: utf-8 -*-
import MySQLdb
import xml.etree.ElementTree as ET
import datetime
import os
import importer.util

def do_import(db, base_dir):
    ARTICLES_DIR = os.path.join(base_dir, "articles")

    if not os.path.exists(ARTICLES_DIR):
        os.makedirs(ARTICLES_DIR)
    
    cur = db.cursor()
    cur.execute("select n.nid, n.title, n.created, n.changed, r.title, r.body, r.log, r.vid from node n inner join node_revisions r on r.vid = n.vid where n.type = 'page'")

    counter = 0
    for a_result in cur.fetchall():
        counter = counter + 1
        uid = str(a_result[0])
        vid = str(a_result[7])

        filename = "%s.xml" % uid
        filepath = os.path.join(ARTICLES_DIR, filename)

        if os.path.exists(filepath):
            print("skipping %s" % filename)
            continue
        
        print("-----------------------------------------%s,%s" % (uid, vid))
        
        xml_article = ET.Element('article')
        xml_article.set('uid', uid)
        xml_article.set('revision', vid)
    
        ET.SubElement(xml_article, 'title').text = a_result[4]
        xml_content = ET.SubElement(xml_article, 'content')
        xml_content.text = a_result[5]
        xml_content.set('content-type', 'wiki')
        ET.SubElement(xml_article, 'created_at').text = util.ts_to_utc(a_result[2])

        cur_tags = db.cursor()
        cur_tags.execute("select name, tid from term_data where tid in (select tid from term_node where nid = %s and vid = %s)" % (uid, vid))

        # tags
        xml_tags = ET.SubElement(xml_article, 'tags')
        for t_result in cur_tags.fetchall():
            tag_str = t_result[0]

            xml_tag = ET.SubElement(xml_tags, 'tag')
            xml_tag.text = tag_str
        

        ET.ElementTree(xml_article).write(filepath, "UTF-8", True)
        ET.dump(xml_article)

        # content_type_page: enthält felder wie gog, humble store, steam

    return counter
