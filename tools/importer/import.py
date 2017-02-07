#!/usr/bin/python3
# -*- coding: utf-8 -*-
import MySQLdb
import xml.etree.ElementTree as ET
import os

import importer.users, importer.articles, importer.news

BASE_DIR=os.path.join('/tmp','import')
ARTICLES_DIR=os.path.join(BASE_DIR, 'articles')

db = MySQLdb.connect(host="localhost", user="export", passwd="export", db="holarse", charset='utf8')

count = importer.users.do_import(db, BASE_DIR)
print("imported %d users" % count)
    
# get all articles
count = importer.articles.do_import(db, BASE_DIR)
print("imported %d articles" % count)

# get all news
count = importer.news.do_import(db, BASE_DIR)
print("imported %d news" % count)

# get all forum posts

db.close()
