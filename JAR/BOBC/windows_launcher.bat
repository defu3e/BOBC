@Echo Off

For /F "Tokens=1,2 Skip=1" %%i In (
   'WMIC OS list free'
   ) Do If Not "%%j"=="" Set $MEM=%%i

IF %$MEM% GEQ 2000000 (
cd bobc
start javaw -Xmx1500m -jar BOBC.jar ok
) ELSE (
cd bobc
start javaw -jar BOBC.jar war_msg
)
EXIT 0
