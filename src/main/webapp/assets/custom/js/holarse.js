var holarse = {};
holarse.csrf_token  = $("meta[name=_csrf]").attr("content");
holarse.csrf_header = $("meta[name=_csrf_header]").attr("content");