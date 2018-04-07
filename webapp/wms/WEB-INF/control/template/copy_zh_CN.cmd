rem $Id: copy_zh_CN.cmd,v 1.1.1.1 2008/01/02 08:29:53 administrator Exp $
rem Copy and Delete Chinese Template XML files
rem 2006/12/06 N.Sawa
@echo OFF
echo Please select ..
echo 1.copy   2.delete   3.EXIT
set /p action=
if %action%==1 goto COPY_FILES
if %action%==2 goto DELETE_FILES
if %action%==3 goto EXIT
echo Invalid input.
goto END

:COPY_FILES
@echo ON
copy  BarCodeTextBox-template.xml        BarCodeTextBox-template_zh_CN.xml
copy  CheckBox-template.xml              CheckBox-template_zh_CN.xml
copy  DateTextBox-template.xml           DateTextBox-template_zh_CN.xml
copy  FileBrows-template.xml             FileBrows-template_zh_CN.xml
copy  FixedListCell-template.xml         FixedListCell-template_zh_CN.xml
copy  FormatTextBox-template.xml         FormatTextBox-template_zh_CN.xml
copy  FreeTextBox-template.xml           FreeTextBox-template_zh_CN.xml
copy  GanttChart-template.xml            GanttChart-template_zh_CN.xml
copy  GeneralChart-template.xml          GeneralChart-template_zh_CN.xml
copy  HorizontalBarChart-template.xml    HorizontalBarChart-template_zh_CN.xml
copy  ImageButton-template.xml           ImageButton-template_zh_CN.xml
copy  ImageLink-template.xml             ImageLink-template_zh_CN.xml
copy  Label-template.xml                 Label-template_zh_CN.xml
copy  LinkButton-template.xml            LinkButton-template_zh_CN.xml
copy  LinkedPullDown-template.xml        LinkedPullDown-template_zh_CN.xml
copy  LinkLabel-template.xml             LinkLabel-template_zh_CN.xml
copy  ListBox-template.xml               ListBox-template_zh_CN.xml
copy  ListCell-template.xml              ListCell-template_zh_CN.xml
copy  Message-template.xml               Message-template_zh_CN.xml
copy  NumberTextBox-template.xml         NumberTextBox-template_zh_CN.xml
copy  Pager-template.xml                 Pager-template_zh_CN.xml
copy  PasswordTextBox-template.xml       PasswordTextBox-template_zh_CN.xml
copy  PieChart-template.xml              PieChart-template_zh_CN.xml
copy  PullDown-template.xml              PullDown-template_zh_CN.xml
copy  RadioButton-template.xml           RadioButton-template_zh_CN.xml
copy  ScrollListCell-template.xml        ScrollListCell-template_zh_CN.xml
copy  SubmitButton-template.xml          SubmitButton-template_zh_CN.xml
copy  SubmitLabel-template.xml           SubmitLabel-template_zh_CN.xml
copy  Tab-template.xml                   Tab-template_zh_CN.xml
copy  TextArea-template.xml              TextArea-template_zh_CN.xml
copy  TimeTextBox-template.xml           TimeTextBox-template_zh_CN.xml
copy  VerticalBarLineChart-template.xml  VerticalBarLineChart-template_zh_CN.xml
@echo OFF
goto END

:DELETE_FILES
@echo ON
del  *_zh_CN.xml
@echo OFF
goto END

:EXIT
rem echo exit.
goto END

:END
echo Program will exit.
pause
