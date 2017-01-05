#!/usr/bin/python2
# -*- coding: utf-8 -*-
import MySQLdb
import xml.etree.ElementTree as ET
import os
import util
import users, articles

BASE_DIR=os.path.join('tmp','import')
ARTICLES_DIR=os.path.join(BASE_DIR, 'articles')

db = MySQLdb.connect(host="localhost", user="export", passwd="export", db="holarse", charset='utf8')

count = users.do_import(db, BASE_DIR)
print "imported %d users" % count
    
# get all articles
count = articles.do_import(db, BASE_DIR)
print "imported %d articles" % count

# get all news

# get all forum posts

db.close()
