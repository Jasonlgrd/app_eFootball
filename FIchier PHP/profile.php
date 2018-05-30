<?php

$db = new PDO("mysql:host=jasonlagcp1.mysql.db;dbname=jasonlagcp1", "jasonlagcp1", "Jason17240");
$results["error"] = false;
$results["message"] = [];

if(!empty($_POST)){
	
	if(!empty($_POST["id"])){
		
		$id = $_POST["id"];
		
		$sql = $db->prepare("SELECT * FROM app_users WHERE user_id = :id");
		$sql->execute([":id" => $id]);
		$row = $sql->fetch(PDO::FETCH_OBJ);
		if($row){
			$results["error"] = false;
			$results["id"] = $row->user_id;
			$results["nom"] = $row->user_nom;
			$results["prenom"] = $row->user_prenom;
			$results["pseudo"] = $row->user_pseudo;
			$results["email"] = $row->user_email;
		}else{
			$results["error"] = true;
			$results["message"] = "Une erreur est survenue";
		}
	}else{
		$results["error"] = true;
		$results["message"] = "Une erreur est survenue";
	}
	
	echo json_encode($results);
	
}

?>