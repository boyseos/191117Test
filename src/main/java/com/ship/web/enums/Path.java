package com.ship.web.enums;
public enum Path {
    UPLOAD_PATH, CRAWLING_OPGG, CRAWLING_CGV,CRAWLING_BUGS,CRAWLING_ENG;
    @Override
    public String toString() {
        String result = "";
        switch (this) {
        case CRAWLING_CGV:
            result= "http://www.cgv.co.kr/movies/?lt=";
            break;
        case CRAWLING_BUGS:
        	result= "https://music.bugs.co.kr/chart";
        	break;
        case CRAWLING_ENG:
        	result= "https://endic.naver.com/?sLn=kr";
        		break;
        case UPLOAD_PATH:
            result = "C:\\Users\\User\\git\\EGOV_SHIP\\src\\main\\webapp\\resources\\upload\\";
            break;
        case CRAWLING_OPGG :
            result= "https://talk.op.gg/s/lol/free?page=";
            break;
        }
       return result;
   }
}