<?php
$target_path1 = "uploads/";
//Add the original filename to our target path.
//Result is "uploads/filename.extension" */
if (isset($_POST['tag']) && $_POST['tag'] != '') 
{
    // get tag
     $tag = $_POST['tag'];
     $response = array("tag" => $tag, "error" => FALSE);
     if($tag=='textmsg')
     {
     	 $college=$_POST['college'];
         $email=$_POST['email'];
         $post=$_POST['post'];
         $branch=$_POST['branch'];
         $semester=$_POST['semester'];
         $text=$_POST['text'];
         $conn=mysqli_connect("localhost","root","","android_api");
          if (!$conn)
          {
          die("Connection failed: " . mysqli_connect_error());
          }
          $sql="INSERT INTO texts VALUES('id','$email','$text')";
          $result=mysqli_query($conn,$sql);
          if($result!=false)
          {
          	$response["error"]=FALSE;
          }
         /* if($post=='Director')
          {
          $result1=mysqli_query($conn,"select email from users where college='$college' && branch='$branch'");
          //$result1=mysqli_query($conn,$sql1);
          while ($row = mysqli_fetch_array($result1))
           {
           	$i=$row['email'];
            $result2=mysqli_query($conn,"insert into texts values('id','$i','$text')") ; 
           }  
         }
          if($post=='HOD')
          {
          $result1=mysqli_query($conn,"select email from users where college='$college' && branch='$branch' && semester='$semester' && post !='HOD'");
          //$result1=mysqli_query($conn,$sql1);
          while ($row = mysqli_fetch_array($result1))
           {
           	$i=$row['email'];
            $result2=mysqli_query($conn,"insert into texts values('id','$i','$text')") ; 
           }  
         }
          if($post=='Professor')
          {
          $result1=mysqli_query($conn,"select email from users where college='$college' && branch='$branch' && semester='$semester'&& post='Student'");
          //$result1=mysqli_query($conn,$sql1);
          while ($row = mysqli_fetch_array($result1))
           {
           	$i=$row['email'];
            $result2=mysqli_query($conn,"insert into texts values('id','$i','$text')") ; 
           }  
         }*/
          
     }
}
else
{
$target_path1 = $target_path1 . basename( $_FILES['uploaded_file']['name']);
$uploadOk=1;
if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $target_path1))
{
    echo "The first file ".  basename( $_FILES['uploaded_file']['name']).
    " has been uploaded.";
}
else
{
    echo "There was an error uploading the file, please try again!";
    echo "filename: " .  basename( $_FILES['uploaded_file']['name']);
    echo "target_path: " .$target_path1;
}
$college=$_POST['college'];
$email=$_POST['email'];
$branch=$_POST['branch'];
$post=$_POST['post'];
$semester=$_POST['semester'];
$conn=mysqli_connect("localhost","root","","android_api");
 if (!$conn)
    {
     die("Connection failed: " . mysqli_connect_error());
     }
  $sql="INSERT INTO images VALUES('id','$email','$target_path1')";
  $result=mysqli_query($conn,$sql);
/*if($post == 'Director')
{
	
           $result1=mysqli_query($conn,"select email from users where college='$college' && branch='$branch'");
          //$result1=mysqli_query($conn,$sql1);
          while ($row = mysqli_fetch_array($result1))
           {
           	$i=$row['email'];
            $result2=mysqli_query($conn,"insert into images values('id','$i','$target_path1')") ; 
           }  
}
if($post == 'HOD')
{
	
           $result1=mysqli_query($conn,"select email from users where college='$college' && branch='$branch' && semester='$semester'");
          //$result1=mysqli_query($conn,$sql1);
          while ($row = mysqli_fetch_array($result1))
           {
           	$i=$row['email'];
            $result2=mysqli_query($conn,"insert into images values('id','$i','$target_path1')") ; 
           }  
}
if($post == 'Professor')
{
	
           $result1=mysqli_query($conn,"select email from users where college='$college' && branch='$branch' && semester='$semester' && post='Student'");
          //$result1=mysqli_query($conn,$sql1);
          while ($row = mysqli_fetch_array($result1))
           {
           	$i=$row['email'];
            $result2=mysqli_query($conn,"insert into images values('id','$i','$target_path1')") ; 
           }  
}*/

}
?>