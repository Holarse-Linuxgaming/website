# -*- coding: utf-8 -*-
import pymysql
import xml.etree.ElementTree as ET
import datetime
import os
import importer.util as util

def do_import(db, base_dir):
    ARTICLES_DIR = os.path.join(base_dir, "articles")

    ET.register_namespace('holarse', "http://holarse.de/entity/importer/")

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

        xml_article = ET.Element('{http://holarse.de/entity/importer/}article')
        xml_article.set('uid', uid)
        xml_article.set('revision', vid)
    
        xml_titles = ET.SubElement(xml_article, 'titles')

        xml_title = ET.SubElement(xml_titles, 'title')
        xml_title.text = a_result[4]
        xml_title.set('type', 'MAIN')

        xml_content = ET.SubElement(xml_article, 'content')
        xml_content.text = a_result[5]
        xml_content.set('type', 'WIKI')
        ET.SubElement(xml_article, 'created_at').text = util.ts_to_utc(a_result[2])

        cur_tags = db.cursor()
        cur_tags.execute("select name, tid from term_data where tid in (select tid from term_node where nid = %s and vid = %s)" % (uid, vid))

        # tags
        xml_tags = ET.SubElement(xml_article, 'tags')
        for t_result in cur_tags.fetchall():
            tag_str = t_result[0]

            xml_tag = ET.SubElement(xml_tags, 'tag')
            xml_tag.text = tag_str

        # homepage
        cur_hp = db.cursor()
        cur_hp.execute("select field_homepage_value from content_field_homepage where nid = %s and vid = %s" % (uid, vid))
        link_tags = ET.SubElement(xml_article, 'links')
        for l_result in cur_hp.fetchall():

            xml_hp = ET.SubElement(link_tags, 'link')
            xml_hp.text = l_result[0]
            xml_hp.set('type', 'WEBSITE')

        # wine/crossover
        cur_wine = db.cursor()
        cur_wine.execute("select field_winehq_value, field_crossoverdb_value from content_type_page where nid = %s and vid = %s" % (uid, vid))
        for w_result in cur_wine.fetchall():
            # winehq
            if w_result[0]:
                xml_winehq = ET.SubElement(link_tags, 'link')
                xml_winehq.text = w_result[0]
                xml_winehq.set('type', 'WINEHQ')

            # crossover
            if w_result[1]:
                xml_crossover = ET.SubElement(link_tags, 'link')
                xml_crossover.text = w_result[1]
                xml_crossover.set('type', 'CROSSOVERDB')
            
        # shops
        cur_shops = db.cursor()
        cur_shops.execute("select field_steam_value, field_humblestore_value, field_gog_value, field_ownshop_value, field_itchio_value from content_type_page where nid = %s and vid = %s" % (uid, vid))
        shop_tags = ET.SubElement(xml_article, 'shops')
        for s_result in cur_shops.fetchall():

            # steam
            if s_result[0]:
                xml_steam = ET.SubElement(shop_tags, 'shop')
                xml_steam.text = s_result[0]
                xml_steam.set('type', 'STEAM')

            # humblestore
            if s_result[1]:
                xml_hs = ET.SubElement(shop_tags, 'shop')
                xml_hs.text = s_result[1]
                xml_hs.set('type', 'HUMBLESTORE')

            # gog
            if s_result[2]:
                xml_gog = ET.SubElement(shop_tags, 'shop')
                xml_gog.text = s_result[2]
                xml_gog.set('type', 'GOG')

            # ownshop
            if s_result[3]:
                xml_os = ET.SubElement(shop_tags, 'shop')
                xml_os.text = s_result[3]
                xml_os.set('type', 'OWNSHOP')

            # itch
            if s_result[4]:
                xml_itch = ET.SubElement(shop_tags, 'shop')
                xml_itch.text = s_result[4]
                xml_itch.set('type', 'ITCH')
                
        ET.ElementTree(xml_article).write(filepath, "UTF-8", True)
        #ET.dump(xml_article)

    return counter
