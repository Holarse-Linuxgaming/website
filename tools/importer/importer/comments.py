# -*- coding: utf-8 -*-
import pymysql
import xml.etree.ElementTree as ET
import datetime
import os
import importer.util as util

def do_import(db, base_dir):
    COMMENTS_DIR = os.path.join(base_dir, "comments")

    ET.register_namespace('holarse', "http://holarse.de/entity/importer/")

    if not os.path.exists(COMMENTS_DIR):
        os.makedirs(COMMENTS_DIR)
    
    cur = db.cursor()
    cur.execute("select c.cid, c.nid, c.comment, c.timestamp, c.status, c.name from comments c")

    counter = 0
    for a_result in cur.fetchall():
        counter = counter + 1
        uid = str(a_result[0])
        nid = str(a_result[1])
        state = "published" if str(a_result[4]) == "0" else "deleted"

        filename = "%s.xml" % uid
        filepath = os.path.join(COMMENTS_DIR, filename)

        if os.path.exists(filepath):
            print("skipping %s" % filename)
            continue
        
        print("-----------------------------------------%s,%s" % (uid, nid))

        xml_comment = ET.Element('{http://holarse.de/entity/importer/}comment')
        xml_comment.set('uid', uid)
        xml_comment.set('attached-to', nid)

        xml_content = ET.SubElement(xml_comment, 'state')
        xml_content.text = state

        xml_content = ET.SubElement(xml_comment, 'author')
        xml_content.text = a_result[5]

        xml_content = ET.SubElement(xml_comment, 'content')
        xml_content.text = a_result[2]
        xml_content.set('type', 'WIKI')
        ET.SubElement(xml_comment, 'created_at').text = util.ts_to_utc(a_result[3])

        ET.ElementTree(xml_comment).write(filepath, "UTF-8", True)
        #ET.dump(xml_comment)

    return counter
