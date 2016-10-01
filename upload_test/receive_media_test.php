<?php

if (isset($_POST['tag']) && $_POST['tag'] != '') 
{
    // get tag
     $tag = $_POST['tag'];
     $response = array("tag" => $tag, "error" => FALSE);
     if($tag=='receivemsg')
     {
     	$email=$_POST['email'];
     	 $conn=mysqli_connect("localhost","root","","android_api");
          if (!$conn)
          {
          die("Connection failed: " . mysqli_connect_error());
          }
          $result1=mysqli_query($conn,"select text from texts where email='$email' ");
          if($result1)
          {
         while($row=mysqli_fetch_array($result1,MYSQL_ASSOC))
          {
          	               $result[]=array('text'=>$row['text']);
          }

         //print_r($result);
          $respone["error"]=FALSE;
          $response["detail"]=$result;
          echo json_encode($response);
          }
          else
          {
          	         $response["error"] = TRUE;
                       $response["error_msg"] = "Error occured in Retrieve";
                         echo json_encode($response);
          }
     }
  }

 ?>
          