#!/bin/bash

S=`stat -c "%a" bobc/cpp/AES.exe ` 
if [ "$S" != 777 ]
then
chmod -R 777 bobc/cpp
fi

mem=$(grep MemAvailable /proc/meminfo | awk '{print $2}')


I=`dpkg -s wine | grep "Status" ` 
if [ -n "$I" ] 
then
	if [ "$mem" -ge 2000000 ] 
        then  
            cd bobc
            java -Xmx1500m -jar BOBC.jar ok
    else 
        cd bobc
        java -jar BOBC.jar war_msg
	fi
    
else
   echo -e "\nДля корректной работы программы необходимо установить пакет wine\n"
   echo "Установка wine.."
   sudo apt-get install wine
   winecfg
    
    if [ "$mem" -ge 2000000 ] 
        then  
            cd bobc
            java -Xmx1500m -jar BOBC.jar ok
    else 
        cd bobc
        java -jar BOBC.jar war_msg
	fi

fi

exit 0
