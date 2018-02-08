#!/usr/bin/python3
# -*- coding: utf-8 -*-
import pymysql.cursors
import xml.etree.ElementTree as ET
import os
import configparser

import importer.users, importer.articles, importer.news, importer.comments

# read config
config = configparser.ConfigParser()
config.read(os.path.join("config.ini"))

BASE_DIR=config["export"]["base_dir"]
ARTICLES_DIR=os.path.join(BASE_DIR, 'articles')

db = pymysql.connect(unix_socket=config["database"]["unix_socket"], database=config["database"]["db"], user=config["database"]["user"], passwd=config["database"]["passwd"])

count_u = importer.users.do_import(db, BASE_DIR)
print("imported %d users" % count_u)
    
# get all articles
count_a = importer.articles.do_import(db, BASE_DIR)
print("imported %d articles" % count_a)

# get all news
count_n = importer.news.do_import(db, BASE_DIR)
print("imported %d news" % count_n)

# get all comments
count_c = importer.comments.do_import(db, BASE_DIR)
print("imported %d comments" % count_c)

# get all forum posts

db.close()
#
print("export completed with %d objects to %s" % (count_u + count_a + count_n + count_c, BASE_DIR))
