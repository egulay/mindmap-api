#!/usr/bin/env bash
function jsonval {
    temp=`echo $json | \
    sed 's/\\\\\//\//g' | \
    sed 's/[{}]//g' | \
    awk -v k="text" '{n=split($0,a,","); for (i=1; i<=n; i++) print a[i]}' | \
    sed 's/\"\:\"/\|/g' | sed 's/[\,]/ /g' | sed 's/\"//g' | grep -w $prop`
    echo ${temp##*|}
}

json=$(curl POST -vu mindmap:secret http://localhost:8080/oauth/token -H \
"Accept: application/json" \
-d "password=password&username=admin&grant_type=password&scope=read%20write&client_secret=secret&client_id=mindmap")

export PATH=$PATH:/c/Users/emreg/AppData/Local/Programs/Python/Python36

prop='access_token'
token=`jsonval`

echo ''
echo '------ FULL RESPONSE ------'
echo ${json} | python -m json.tool
echo '------ FULL RESPONSE ------'
echo ''

echo ''
echo '------ ACCESS TOKEN ------'
echo ${token}
echo '------ ACCESS TOKEN ------'
echo ''
echo '------ GET ALL USERS ------'
curl -H "Authorization: Bearer ${token}" -X GET http://localhost:8080/api/user/list/ | python -m json.tool
echo '------ GET ALL USERS ------'
