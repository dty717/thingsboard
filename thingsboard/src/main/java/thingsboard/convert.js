    function jump(){
        if(!editor){
            return;
        }
        var selected=editor.getSelectedText();
        if(selected==""){
            return;
        }
        var index=path.indexOf("src/main/java/");
        if(index==-1){
            return;
        }
        var javaPath=path.substring(index+14);
        var packages=javaPath.split("/");
        var reg=new RegExp(packages[0]+'\..+;')
        var s=selected.match(reg);
        if(s&&s.length>0){
            s=s[0].substring(0,s[0].length-1).split('.');
            var newPath=path.substring(0,index+14);
            for(var i=0;i<s.length-1;i++){
                newPath+=s[i]+"/";
            }
            switch (s[2]) {
                case 'server':
                    if(s[3]=="common"){
                        if(s[4]=="transport"){
                            newPath=newPath.replace(/thingsboard\/.+src/,"thingsboard/common/"+s[4]+"/"+s[5]+"/src");
                        }else{
                            newPath=newPath.replace(/thingsboard\/.+src/,"thingsboard/common/"+s[4]+"/src");
                        }                        
                    }else if(s[3]=="dao"){
                        newPath=newPath.replace(/thingsboard\/.+src/,"thingsboard/dao/src");
                    }else if(s[3]=="kafka"){
                        newPath=newPath.replace(/thingsboard\/.+src/,"thingsboard/common/queue/src");
                    }else{
                        newPath=newPath.replace(/thingsboard\/.+src/,"thingsboard/application/src");
                    }
                    break;
                case "rule":
                    if(s[4]=="api"){
                        newPath=newPath.replace(/thingsboard\/.+src/,"thingsboard/rule-engine/rule-engine-api/src");
                    }else{
                        newPath=newPath.replace(/thingsboard\/.+src/,"thingsboard/rule-engine/rule-engine-components/src");
                    }
                    break;
                default:
                    return;
                    // code
            }
            var newFile=s[s.length-1]+".java";
            // console.log(newPath+" "+newFile);
            path=newPath;
            fresh();
            currentFile=newFile;
            clickShowFile({innerText:newFile});
        }
    }
    function getConstants(){
        if(!editor){
            //return;
        }
        var selected=c//editor.getSelectedText();
        if(selected==""){
            //return;
        }
        var match=selected.match(/public static final String.+=.+;/g)
        if(match){
            for(var i=0;i<match.length;i++){
                var tem=match[i].substring(27).trim();
                if(tem.indexOf("(")!=-1){
                    tem=tem.split("=",2)
                    tem=tem[0]+"="+"\""+tem[1]+"\"";
                }
                eval(tem);
                eval("ModelConstants."+tem);
            }
        }
        
        // editor.setValue(str);
        
    }
    function getSQL(){
        if(!editor){
            //return;
        }
        var selected=s//editor.getSelectedText();
        if(selected==""){
            //return;
        }
        var lines=selected.split('\n');
        var lineIndex=0;
        var len=lines.length;
        var _return;
        if(lines[1][0]=='\r'){
            _return='\r\n';
        }else{
            _return='\n';
        }
        var str="";
        //judge type
        var type="";
        for(;lineIndex<len;lineIndex++){
            if(lines[lineIndex].indexOf("select")!=-1){
                type="select";
                break;
            }else if(lines[lineIndex].indexOf("update")!=-1){
                type="update";
                break;
            }if(lines[lineIndex].indexOf("delete")!=-1){
                type="delete";
                break;
            }if(lines[lineIndex].indexOf("insert")!=-1){
                type="insert";
                break;
            }if(lines[lineIndex].indexOf("select")!=-1){
                type="select";
                break;
            }
        }
        if(type=="select"){
            var selectNode={};
            for(;lineIndex<len;lineIndex++){
                var _match
                if(lines[lineIndex].indexOf('from')!=-1){
                    _match=lines[lineIndex].match(/from\([\w\d\s\t\._$]*\)/);
                    if(_match&&(_match.length!=0)){
                        var _tem=_match[0].substring(5,_match[0].length-1);
                        if(_tem!=""){
                            selectNode.from=_tem;
                        }
                        lines[lineIndex]=lines[lineIndex].replace(_match[0],"");
                    }
                    
                }
                if(lines[lineIndex].indexOf('where')!=-1){
                    _match=lines[lineIndex].match(/where\((?:[^)(]+|\((?:[^)(]+|\([^)(]*\))*\))*\)/);
                    if(_match&&(_match.length!=0)){
                        var _tem=_match[0].substring(6,_match[0].length-1).trim();
                        if(_tem.startsWith("eq")){
                            var eq=_tem.substring(3,_tem.length-1).split(",",2);
                            if(!selectNode.eq){
                                selectNode.eq=new Map();
                            }else{
                                if(eq.length==2){
                                    eq[1]=eq[1].replace(/get[\w_].*\(\)/,function(e){
                                    	return e[3].toLowerCase()+e.substring(4,e.length-2);;
                                    })
                                    selectNode.eq.set(eq[0],eq[1]);    
                                }
                            }
                        }
                        lines[lineIndex]=lines[lineIndex].replace(_match[0],"");
                    }
                }

                if(lines[lineIndex].indexOf('and')!=-1){
                    _match=lines[lineIndex].match(/and\((?:[^)(]+|\((?:[^)(]+|\([^)(]*\))*\))*\)/g);
                    if(_match&&(_match.length!=0)){
                        for(var i=0;i<length;i++){
                            var _tem=_match[i].substring(4,_match[i].length-1).trim();
                            if(_tem.startsWith("eq")){
                                var eq=_tem.substring(3,_tem.length-1).split(",",2);
                                if(!selectNode.eq){
                                    selectNode.eq=new Map();
                                }else{
                                    if(eq.length==2){
                                        eq[1]=eq[1].replace(/get[\w_].*\(\)/,function(e){
                                        	return e[3].toLowerCase()+e.substring(4,e.length-2);;
                                        })
                                        selectNode.eq.set(eq[0],eq[1]);    
                                    }
                                }
                            }
                            lines[lineIndex]=lines[lineIndex].replace(_match[i],"");
                        }
                    }
                }
                if(lines[lineIndex].indexOf('limit')!=-1){
                    _match=lines[lineIndex].match(/limit\([\w\d\s\t\._$]*\)/);
                    if(_match&&(_match.length!=0)){
                        var _tem=_match[0].substring(6,_match[0].length-1);
                        if(_tem!=""){
                            selectNode.limit=_tem;
                        }
                        lines[lineIndex]=lines[lineIndex].replace(_match[0],"");
                    }
                }
                if(lines[lineIndex].indexOf('orderBy')!=-1){
                    _match=lines[lineIndex].match(/orderBy\((?:[^)(]+|\((?:[^)(]+|\([^)(]*\))*\))*\)/);
                    if(_match&&(_match.length!=0)){
                        var _tem=_match[0].substring(8,_match[0].length-1);
                        if(_tem!=""){
                            if(!selectNode.orderBy){
                                selectNode.orderBy={asc:[],desc:[]};
                            }
                            var match_=_tem.match(/asc\([\w\d\s\t\._$]*\)/g);
                            if(match_){
                                for (var j = 0; j < match_.length; j++) {
                                    selectNode.orderBy.asc.push(match_[j].substring(4,match_[j].length-1).trim());
                                }
                            }
                            match_=_tem.match(/desc\([\w\d\s\t\._$]*\)/g);
                            if(match_){
                                for (var j = 0; j < match_.length; j++) {
                                    selectNode.orderBy.desc.push(match_[j].substring(5,match_[j].length-1).trim());
                                }
                            }
                        }
                        lines[lineIndex]=lines[lineIndex].replace(_match[0],"");
                    }
                }
            }
            
            var sql="    <select>\r\n        SELECT\r\n";
            if(selectNode.json){
                sql+="JSON ";
            }
            if(selectNode.distinct){
                sql+="DISTINCT ";
            }
            if (!selectNode.columnNames) {
                sql+=('*');
            } else {
                //Utils.joinAndAppendNames(builder, codecRegistry, columnNames);
            }
            sql+=" FROM ";
            sql+=eval(selectNode.from);
            if (selectNode.eq) {
                sql+=" WHERE ";
                selectNode.eq.forEach(function(value,key){
                    sql+="`"+eval(key)+"`"+"="+"#{"+value.trim()+"}"+" AND ";
                })
                sql=sql.substring(0,sql.length-5)
            }
            if(selectNode.group){
                
            }
            if(selectNode.orderBy){
                sql+=" ORDER BY ";
                
                for(var i=0;i<selectNode.orderBy.asc.length;i++){
                    sql+="`"+eval(selectNode.orderBy.asc[i])+"`"+" ASC,"
                }
                for(var i=0;i<selectNode.orderBy.desc.length;i++){
                    sql+="`"+eval(selectNode.orderBy.desc[i])+"`"+" DESC,"
                }
                sql=sql.substring(0,sql.length-1);
            }
            if(sql.limit){
                sql+=" LIMIT "+sql.limit;
            }
            sql+="\r\n    </select>"
            console.log(sql)
        }

        // editor.setValue(str);
    
        
        
    }
    
    function move(){
        var relativePath=path.substring(0,path.substring(0,path.length-1).lastIndexOf('/'));
        if(!relativePath.endsWith("dao")){
            return;
        }else if(!currentFile.endsWith(".java")){
            return;
        }
        var s=currentFile.substring(0,currentFile.length-5)+"Mapper";
        $.ajax({
            url: "newFile",
            method: "GET",
            data: {path:relativePath+"/mapper/",name:s+".java"},
            contentType: "text/plain",
            success: (function (msg) {
                console.log(msg);
            })
        })
        $.ajax({
            url: "newFile",
            method: "GET",
            data: {path:relativePath+"/mapper/",name:s+".xml"},
            contentType: "text/plain",
            success: (function (msg) {
                console.log(msg);
            })
        })
        var xmlContent="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"+
"<!--\r\n"+
"\r\n"+
"       Copyright 2010-2016 the original author or authors.\r\n"+
"\r\n"+
"       Licensed under the Apache License, Version 2.0 (the \"License\");\r\n"+
"       you may not use this file except in compliance with the License.\r\n"+
"       You may obtain a copy of the License at\r\n"+
"\r\n"+
"          http://www.apache.org/licenses/LICENSE-2.0\r\n"+
"\r\n"+
"       Unless required by applicable law or agreed to in writing, software\r\n"+
"       distributed under the License is distributed on an \"AS IS\" BASIS,\r\n"+
"       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\r\n"+
"       See the License for the specific language governing permissions and\r\n"+
"       limitations under the License.\r\n"+
"\r\n"+
"-->\r\n"+
"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\r\n"+
"        \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n"+
"\r\n"+
"<mapper namespace=\"thingsboard.dao.mapper."+s+"\">\r\n"+
"\r\n"+
"<!--    <select id=\"test\" resultType=\"String\">\r\n"+
"        SELECT\r\n"+
"            table_name\r\n"+
"             FROM `common_id` WHERE `ID` LIKE \'123xsw2d2-1213-df33f\'\r\n"+
"    </select>-->\r\n"+
"<!--    <select id=\"getAddrByNameAndCompany\" resultType=\"ValueCenter.domain.Addr\">\r\n"+
"        SELECT * FROM ${company} WHERE `name` LIKE #{name}\r\n"+
"    </select>\r\n"+
"    \r\n"+
"    <select id=\"getCommonID\" resultType=\"ValueCenter.domain.CommonID\">\r\n"+
"        SELECT \r\n"+
"            `ID` as \"id\",\r\n"+
"            table_name as \"tableName\",\r\n"+
"            location as \"location\",\r\n"+
"            company as \"company\"\r\n"+
"        FROM `common_id` WHERE `ID` LIKE #{id}\r\n"+
"    </select>-->\r\n"+
"    <!--\r\n"+
"    <insert id=\"insertThing\" parameterType=\"Thing\">\r\n"+
"        INSERT INTO Thing\r\n"+
"        (东西,位置,图片路径,常用,数量,特性)\r\n"+
"        VALUES\r\n"+
"        (#{name}, #{location}, #{picture}, #{oftenUsed}, #{count},  #{attrs})\r\n"+
"    </insert>\r\n"+
"\r\n"+
"    \r\n"+
"    <select id=\"getTags\" resultType=\"String\">\r\n"+
"        SELECT (标签名) FROM 性质标签\r\n"+
"    </select>\r\n"+
"    <select id=\"getThings\" resultType=\"Thing\">\r\n"+
"        SELECT\r\n"+
"            东西 as \"name\",\r\n"+
"            位置 as \"location\",\r\n"+
"            图片路径 as \"picture\",\r\n"+
"            常用 as \"oftenUsed\",\r\n"+
"            数量 as \"count\",\r\n"+
"            特性 as \"attrs\"\r\n"+
"         FROM Thing limit  #{page},#{pageSize};\r\n"+
"    </select>\r\n"+
"    <select id=\"searchThings\" resultType=\"Thing\">\r\n"+
"        SELECT\r\n"+
"        东西 as \"name\",\r\n"+
"        位置 as \"location\",\r\n"+
"        图片路径 as \"picture\",\r\n"+
"        常用 as \"oftenUsed\",\r\n"+
"        数量 as \"count\",\r\n"+
"        特性 as \"attrs\"\r\n"+
"        FROM Thing WHERE 东西 LIKE #{name} limit  #{page},#{pageSize};\r\n"+
"    </select>-->\r\n"+
"\r\n"+
"\r\n"+
"</mapper>"
        setTimeout(function(){
            $.ajax({
                url: "saveFile",
                method: "POST",
                data: {file: editor.getValue(),path:relativePath+"/mapper/"+s+".java",charset:""},
                success: (function (msg) {
                    console.log(msg);
                })
            })
            $.ajax({
                url: "saveFile",
                method: "POST",
                data: {file: xmlContent,path:relativePath+"/mapper/"+s+".xml",charset:""},
                success: (function (msg) {
                    console.log(msg);
                })
            })
        },500)

    }
    
    function convertMybatis(){
        if(!editor){
            return;
        }
        var selected=editor.getValue();
        if(selected==""){
            return;
        }
       var lines=selected.split('\n');
        var lineIndex=0;
        var len=lines.length;
        var _return;
        if(lines[1][0]=='\r'){
            _return='\r\n';
        }else{
            _return='\n';
        }
         var str="";
        for(;lineIndex<len;lineIndex++){
            if(lines[lineIndex].trim().startsWith("package ")){
                var _line=lines[lineIndex];
                if(_line.indexOf(".mapper;")==-1){
                    _line=_line.replace(/thingsboard.dao.\w+;/,"thingsboard.dao.mapper;")
                }
                str+=_line+"\n"+_return+"import org.apache.ibatis.annotations.Param;";
                lineIndex++;
                break;
            }else{
                str+=lines[lineIndex]+"\n"
            }
        }
        
        for(;lineIndex<len;lineIndex++){
            if(lines[lineIndex].trim().indexOf("interface")!=-1){
                var _line=lines[lineIndex];
                if(_line.indexOf("Mapper")==-1){
                    _line=_line.replace(/interface[\w\s\t]+extends Dao</,function(e){
                    	return "interface "+e.substring(9,e.length-12).trim()+"Mapper extends Dao<";
                    })
                }
                str+=_line+"\n";
                lineIndex++;
                break;
            }else{
                str+=lines[lineIndex]+"\n"
            }
        }
        
        for(;lineIndex<len;lineIndex++){
            var matchLine=lines[lineIndex].match(/\([\s\t,.\[\]\>\<\w]*\)/);
            if(matchLine&&(matchLine.length>0)){
                var params=matchLine[0].substring(1,matchLine[0].length-1);
                if(params.trim()==""){
                    str+=lines[lineIndex]+"\n";
                    continue;
                }
                matchLine=params.split(",");
                var param="(";
                for(var i=0;i<matchLine.length;i++){
                    param+="@Param(\""+matchLine[i].trim().match(/\w+$/)[0]+"\")"+matchLine[i].trim()+", ";
                }
                if(param.length>2){
                    param=param.substring(0,param.length-2);
                }
                param+=")";
                str+=lines[lineIndex].replace(/\([\s\t,.\[\]\>\<\w]*\)/,param)+"\n";
            }else{
                str+=lines[lineIndex]+"\n";
            }
        }
        editor.setValue(str);
    }
    
    function convert(){
        if(!editor){
            return;
        }
        var selected=editor.getSelectedText().trim();
        if(selected==""){
            return;
        }

        var start;
        var range=editor.getSelectionRange()
        if(range.start.row<range.end.row){
            start=range.start
        }else if(range.start.row>range.end.row){
            start=range.end;
        }else if(range.start.column<range.end.column){
            start=range.start
        }else{
            start=range.end;
        }
        var str=selected.replace(/org.thingsboard.server.controller/g,"thingsboard.web")
            .replace(/org.thingsboard.server/g,"thingsboard")
        editor.remove();
        editor.session.insert({row:start.row, column:start.column}, str);
    }