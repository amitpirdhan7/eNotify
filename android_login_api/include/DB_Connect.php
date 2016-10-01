<?php
class DB_Connect 
{
 
    // constructor
    function __construct() 
    {

    }
 
    // destructor
    function __destruct() 
    {
        // $this->close();
    }
 
    // Connecting to database
    public function connect() 
    {
       // include 'Config.php';
        // connecting to mysql
        $con = mysql_connect("localhost","root", "");// or die(mysql_error());
        // selecting database
        mysql_select_db("android_api");// or die(mysql_error());
 
        // return database handler
        return $con;
    }
    
     // Closing database connection
    public function close() {
        mysql_close();
    }
 
}
 
?>