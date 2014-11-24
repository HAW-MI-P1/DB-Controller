DB-Controller
=============

<h1>How to setup DB-Controller</h1>
<ol>
<li>Install a mySQL server. E.g. <a href="http://dev.mysql.com/downloads/mysql/">MySQL.com</a></li>
<li>Create database <b>mip</b></li>
<li>Create table <b>api_results</b><br/>
<code>
CREATE TABLE `mip`.`api_results` (
`k` VARCHAR(45) NOT NULL,
`v` VARCHAR(3255) NULL,
PRIMARY KEY (`k`));
</code></li>
<li>
  Execute code:<br>
  <ul>
    <li>For Test-Setup change uname and password to <b>root</b> (not very safe but purpose rational)</li>
    <li>Use operation <code>DB.connect(String url, String user, String pass);</code></li>
  </ul>
</li>
</ol>
<h3>Necessary Libs in Build Path</h3>
<ul><li><a href="http://dev.mysql.com/downloads/connector/j/">mysql-connector-java-5.1.33-bin.jar</a></li></ul>
