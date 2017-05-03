@Echo Off

chcp 1251

For /F "Tokens=1,2 Skip=1" %%i In (
   'WMIC OS list free'
   ) Do If Not "%%j"=="" Set $MEM=%%i

IF %$MEM% GEQ 2000000 (
cd bobc
java -Xmx1500m -jar BOBC.jar ok
) ELSE (
echo Ќедостаточно свободной оперативной пам€ти.
cd bobc
java -jar BOBC.jar war_msg
)
Pause
