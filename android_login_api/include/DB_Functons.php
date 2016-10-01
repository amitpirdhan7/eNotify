<?php
 
class DB_Functions
 {
 
    private $db;
 
    //put your code here
    // constructor
    function __construct()
     {
        require_once 'DB_Connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
     }
 
    // destructor
    function __destruct()
     {
         
     }
 
    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($college,$post,$name, $email, $password,$password_hint,$branch,$sem)
     {
       
        $result = mysql_query("INSERT INTO users VALUES ('$college','$post','$name','$email','$password','$password_hint','$branch','$sem')");
        // check for successful store
        if ($result) 
        {
            // get user details
            $result = mysql_query("SELECT * FROM users WHERE email = '$email'");
            // return user details
            return mysql_fetch_array($result);
        }
        else 
        {
            return false;
        }
    }
 
    public function getusers($post,$branch,$college,$sem)
    {
        //$result2=Array();
        if($post=='HOD')
        {
        $result=mysql_query("select name,email from users where college='$college' && post='Director'");
         while($row=mysql_fetch_array($result,MYSQL_ASSOC))
         {
           $result2[]=array('name' => $row['name'],'email'=>$row['email']);
         }
         return $result2;
        }
        if($post=='Professor')
        {
         $result=mysql_query("select name,email from users where college='$college' && ((post='Director' && branch='') || ( post='HOD' && branch='$branch')) ");
          while($row=mysql_fetch_array($result,MYSQL_ASSOC))
         {
           $result2[]=array('name' => $row['name'],'email'=>$row['email']);
         }
         return $result2;
        }
        if($post=='Student')
        {
         $result=mysql_query("select name,email from users where college='$college' && ((post='Director' && branch='') || ( post='HOD' && branch='$branch')||(post='Professor' && branch='$branch')) ");
          while($row=mysql_fetch_array($result,MYSQL_ASSOC))
         {
           $result2[]=array('name' => $row['name'],'email'=>$row['email']);
         }
         return $result2;
        }
    }
    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($email, $password) 
    {
        $result = mysql_query("SELECT * FROM users WHERE email = '$email'") or die(mysql_error());
        // check for result
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) 
        {
            $result = mysql_fetch_array($result);
            $password1 = $result['password'];
            // check for password equality
            if ($password1 == $password) 
            {
                // user authentication details are correct
                return $result;
            }
        } 
        else 
        {
            // user not found
            return false;
        }
    }
 
    /**
     * Check user is existed or not
     */
    public function isUserExisted($email) 
    {
        $result = mysql_query("SELECT email from users WHERE email = '$email'");
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) 
        {
            // user existed
            return true;
        } 
        else
         {
            // user not existed
            return false;
         }
    }
 
    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
   /* public function hashSSHA($password) {
 
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }*/
 
    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    /*public function checkhashSSHA($salt, $password) {
 
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
 
        return $hash;
    }*/
 
}
 
?>