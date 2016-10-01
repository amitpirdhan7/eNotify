<?php
 
/**
 * File to handle all API requests
 * Accepts GET and POST
 *
 * Each request will be identified by TAG
 * Response will be JSON data
 * check for POST request
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') 
{
    // get tag
    $tag = $_POST['tag'];
 
    // include db handler
    require_once 'include/DB_Functons.php';
    $db = new DB_Functions();
 
    // response Array
    $response = array("tag" => $tag, "error" => FALSE);
 
    // check for tag type
    if ($tag == 'login') 
    {
        // Request type is check Login
            if((empty($_POST['email']))||(empty($_POST['password'])))
            {
            $response["error"] = TRUE;
            $response["error_msg"] = "please enter email or password";
            echo json_encode($response);
            }
            else
            {
              $email = test_input($_POST['email']);
              $password=$_POST['password'];
               // check for user
              $user = $db->getUserByEmailAndPassword($email, $password);
              if ($user != false) 
               {
            // user found
            $response["error"] = FALSE;
            $response["user"]["college"]=$user["college"];
            $response["user"]["post"]=$user["post"];
            $response["user"]["name"] =  $user["name"];
            $response["user"]["email"] = $user["email"];
            $response["user"]["branch"] = $user["branch"];
            $response["user"]["semester"] = $user["semester"];
            $users=$db->getusers($user["post"],$user["branch"],$user["college"],$user["semester"]);
            $response["user"]["details"]=$users;
            echo json_encode($response);
            // $users=$db->getusers($user["post"],$user["branch"],$user["college"],$user["semester"]);
                               
               //  echo json_encode(array('details'=>$users));
             }
             else 
              {
            // user not found
            // echo json with error = 1
            $response["error"] = TRUE;
            $response["error_msg"] = "Incorrect email or password!";
            echo json_encode($response);
              }
            }
           
    } 
    else if ($tag == 'register')
     {
        // Request type is Register new user 
         

          if ((empty($_POST['name']))||(empty($_POST['password1']))||(empty($_POST['password2']))||(empty($_POST['email']))||(empty($_POST['password_hint'])))
          {
            $response["error"] = TRUE;
            $response["error_msg"] = "please enter all field";
            echo json_encode($response);
          }
          elseif($_POST['password1']!==$_POST['password2'])
          {
            $response["error"] = TRUE;
            $response["error_msg"] = "password donot match";
            echo json_encode($response);
          }
          else
          {
              $college=test_input($_POST['college']);
              $post=test_input($_POST['post']);
              $password=$_POST['password1'];
              
              $password_hint = $_POST['password_hint'];
              $branch= $_POST['branch'];
              $semester=$_POST['semester'];

         
             $name = test_input($_POST['name']);
          // check if name only contains letters and whitespace
                 if (!preg_match("/^[a-zA-Z ]*$/",$name))
                 {
                 $response["error"] = TRUE;
                 $response["error_msg"] = "Only letters and white space allowed";
                 echo json_encode($response);
                 }
                else
                {
                $email = test_input($_POST['email']);
          // check if e-mail address is well-formed
                    if (!filter_var($email, FILTER_VALIDATE_EMAIL))
                    {
                     $response["error"] = TRUE;
                    $response["error_msg"] = "Invalid email format";
                    echo json_encode($response);
                    }
                    else
                    {
                  // check if user is already existed
                        if ($db->isUserExisted($email))
                        {
            // user is already existed - error response
                         $response["error"] = TRUE;
                         $response["error_msg"] = "User already existed";
                         echo json_encode($response);
                        } 
                        else
                       {
            // store user
                       $user = $db->storeUser($college ,$post ,$name ,$email ,$password ,$password_hint ,$branch ,$semester);
                         if ($user)
                            {
                // user stored successfully
                              $response["error"] = FALSE;
                              $response["user"]["college"] = $user["college"];
                              $response["user"]["post"] = $user["post"];
                              $response["user"]["name"] = $user["name"];
                              $response["user"]["email"] = $user["email"];
                              $response["user"]["branch"] = $user["branch"];
                              $response["user"]["semester"] = $user["semester"];
                               $users=$db->getusers($post,$branch,$college,$semester);
                               $response["user"]["details"]=$users;

                               echo json_encode($response);

                             
                               
                              // echo json_encode(array('details'=>$users));
                               
                            }
                            else 
                            {
                // user failed to store
                            $response["error"] = TRUE;
                            $response["error_msg"] = "Error occured in Registartion";
                            echo json_encode($response);
                             }
                      }
                    }

               }
  }  
}  
    
    else 
    {
        // user failed to store
        $response["error"] = TRUE;
        $response["error_msg"] = "Unknow 'tag' value. It should be either 'login' or 'register'";
        echo json_encode($response);
    }
} 
   else
   {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter 'tag' is missing!";
    echo json_encode($response);
    }
          function test_input($data)
            {
            $data = trim($data);
            $data = stripslashes($data);
            $data = htmlspecialchars($data);
            return $data;
            }

?>