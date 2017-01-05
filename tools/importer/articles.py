# -*- coding: utf-8 -*-
import MySQLdb
import xml.etree.ElementTree as ET
import datetime
import os
import util

def do_import(db, base_dir):
    ARTICLES_DIR = os.path.join(base_dir, "articles")
    
    cur = db.cursor()
    cur.execute("select n.nid, n.title, n.created, n.changed, r.title, r.body, r.log from node n inner join node_revisions r on r.vid = n.vid where n.type = 'page'")

    counter = 0
    for a_result in cur.fetchall():
        counter = counter + 1
        uid = str(a_result[0])
        xml_article = ET.Element('article')
        xml_article.set('uid', uid)
    
        ET.SubElement(xml_article, 'title').text = a_result[4]
        xml_content = ET.SubElement(xml_article, 'content')
        xml_content.text = a_result[5]
        xml_content.set('content-type', 'wiki')
        ET.SubElement(xml_article, 'created_at').text = util.ts_to_utc(a_result[2])

        filename = "%s.xml" % uid
        ET.ElementTree(xml_article).write(os.path.join(ARTICLES_DIR, filename), "UTF-8", True)
        ET.dump(xml_article)

        # content_type_page: enthält felder wie gog, humble store, steam

    return counter
