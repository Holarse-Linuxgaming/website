# -*- coding: utf-8 -*-
import datetime

def ts_to_utc(ts):
    return datetime.datetime.utcfromtimestamp(ts).strftime('%Y-%m-%d %H:%M:%S%Z')
