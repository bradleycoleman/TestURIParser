@echo off
setlocal EnableDelayedExpansion
cd C:\Users\bradc\intellij\softeng254-a01

:: Compile
javac -cp junit-4.12.jar;hamcrest-core-1.3.jar;good.jar;. uriparser\TestURIParser.java

:: Loop through jar files
FOR %%A in (good.jar badA.jar badB.jar badC.jar badD.jar badE.jar badF.jar badG.jar badH.jar badI.jar badJ.jar) DO (

echo %%A:

java -cp junit-4.12.jar;hamcrest-core-1.3.jar;%%A;. org.junit.runner.JUnitCore uriparser.TestURIParser
)

pause