# -*- coding: utf-8 -*-
import pymysql
import xml.etree.ElementTree as ET
import datetime
import os
import importer.util as util

def do_import(db, base_dir):
    USERS_DIR=os.path.join(base_dir, 'users')

    ET.register_namespace('holarse', "http://holarse.de/entity/")

    if not os.path.exists(USERS_DIR):
        os.makedirs(USERS_DIR)
    
    # get all users
    cur = db.cursor()
    cur.execute("select u.uid, u.name, u.pass, u.mail, us.signature, u.created, u.login, u.status, u.picture from users u left join users_signature us on us.uid = u.uid")

    count = 0
    
    for u_result in cur.fetchall():
        count = count + 1
        uid = u_result[0]

        filename = "%s.xml" % uid
        filepath = os.path.join(USERS_DIR, filename)

        if os.path.exists(filepath):
            print("skipping %s" % filename)
            continue
        
        # xml user
        xml_user = ET.Element('{http://holarse.de/entity/}user')
        xml_user.set('uid', str(uid))
        ET.SubElement(xml_user, 'created').text = util.ts_to_utc(u_result[5])
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

        ET.ElementTree(xml_user).write(filepath, "UTF-8", True)
        ET.dump(xml_user)

    # field_view mit den zusatzattributen wie rechner, ort usw fehlen hier noch
    return count
