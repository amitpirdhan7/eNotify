<?php
if (isset($_POST['tag']) && $_POST['tag'] != '') 
{
    // get tag
    $tag = $_POST['tag'];
    $response = array("tag" => $tag, "error" => FALSE);
    if($tag == 'forgot')
    {
    	 if((empty($_POST['email']))||(empty($_POST['password']))||(empty($_POST['passwordhint'])))
    	    {
            $response["error"] = TRUE;
            $response["error_msg"] = "please enter all field";
            echo json_encode($response);
            }
            else
            {
            	$passwordhint=$_POST['passwordhint'];
            	$password=$_POST['password'];
            	$email=$_POST['email'];
            	 if (!filter_var($email, FILTER_VALIDATE_EMAIL))
                    {
                     $response["error"] = TRUE;
                    $response["error_msg"] = "Invalid email format";
                    echo json_encode($response);
                    }
                    else
                    {
                    	$conn=mysqli_connect("localhost","root","","android_api");
                        if (!$conn)
                        {
                         die("Connection failed: " . mysqli_connect_error());
                        }
                        else
                        { 
                           $result1=mysqli_query($conn,"select * from users where email='$email'");
                           $result2=mysqli_fetch_assoc($result1);
                           if($passwordhint==$result2['password_hint'])
                           {
                           $result=mysqli_query($conn,"UPDATE users SET password='$password' WHERE email='$email' &&  password_hint='$passwordhint'");
                        	if($result)
                        	{
                        		 $response["error"] = FALSE;
                                $response["error_msg"] = "password changed";
                               echo json_encode($response);
                        	}
                        	else
                        	{
                        		 $response["error"] = TRUE;
                                 $response["error_msg"] = "Error occured in submittion";
                                 echo json_encode($response);
                        	}
                        }
                        else
                        {
                            $response["error"] = TRUE;
                                 $response["error_msg"] = "please check email or password hint";
                                 echo json_encode($response);
                        }
                      
                        }
                        mysqli_close($conn);
                    }
            }
    }

}
?>