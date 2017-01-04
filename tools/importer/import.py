#!/usr/bin/python2
# -*- coding: utf-8 -*-
import MySQLdb
import xml.etree.ElementTree as ET
import datetime
import os

BASE_DIR=os.path.join('tmp','import')
USERS_DIR=os.path.join(BASE_DIR, 'users')

for edir in [USERS_DIR]:
    if not os.path.exists(edir):
        os.makedirs(edir)
        print "created %s" % edir

def ts_to_utc(ts):
    return datetime.datetime.utcfromtimestamp(ts).strftime('%Y-%m-%d %H:%M:%S%Z')

db = MySQLdb.connect(host="localhost", user="export", passwd="export", db="holarse", charset='utf8')

# get all users
cur = db.cursor()

cur.execute("select u.uid, u.name, u.pass, u.mail, us.signature, u.created, u.login, u.status, u.picture from users u left join users_signature us on us.uid = u.uid")

for u_result in cur.fetchall():
    uid = u_result[0]
    
    # xml user
    xml_user = ET.Element('user')
    xml_user.set('uid', str(uid))
    ET.SubElement(xml_user, 'created').text = ts_to_utc(u_result[5])
    ET.SubElement(xml_user, 'signature').text = u_result[4]
    ET.SubElement(xml_user, 'login').text = u_result[1]
    ET.SubElement(xml_user, 'email').text = u_result[3]
    ET.SubElement(xml_user, 'locked').text = 'true' if u_result[7] == 0 else 'false'
    ET.SubElement(xml_user, 'avatar').text = u_result[8]
    
    xml_pass = ET.SubElement(xml_user, 'password')
    xml_pass.text = u_result[2]
    xml_pass.set('type', 'MD5')
    
    # rollen laden
    cur2 = db.cursor()
    cur2.execute("select r.name from users_roles ur inner join role r on r.rid = ur.rid where uid = %s", [uid])

    xml_roles = ET.SubElement(xml_user, 'roles')
    for r_result in cur2.fetchall():
        rolename = r_result[0]

        xml_role = ET.SubElement(xml_roles, 'role')
        xml_role.text = rolename
        xml_roles.append(xml_role)

    filename = "%s.xml" % uid
    ET.ElementTree(xml_user).write(os.path.join(USERS_DIR, filename), "UTF-8", True)

    # field_view mit den zusatzattributen wie rechner, ort usw fehlen hier noch
    

# get all articles
cur = db.cursor()
cur.execute("select n.nid, n.title, n.created, n.changed, r.title, r.body, r.log from node n inner join node_revisions r on r.vid = n.vid where n.type = 'page'")

for a_result in cur.fetchall():
    xml_article = ET.Element('article')
    xml_article.set('uid', str(a_result[0]))
    
    ET.SubElement(xml_article, 'title').text = a_result[4]
    xml_content = ET.SubElement(xml_article, 'content')
    xml_content.text = a_result[5]
    xml_content.set('content-type', 'wiki')
    ET.SubElement(xml_article, 'created_at').text = ts_to_utc(a_result[2])
    
    ET.dump(xml_article)


# content_type_page: enthält felder wie gog, humble store, steam

# get all news

# get all forum posts

db.close()
