<?php

$db = new PDO("mysql:host=jasonlagcp1.mysql.db;dbname=jasonlagcp1", "jasonlagcp1", "Jason17240");
$results["error"] = false;
$results["message"] = [];

if(!empty($_POST)){
	
	if(!empty($_POST["pseudo"]) && !empty($_POST["password"])){
		
		$pseudo = $_POST["pseudo"];
		$password = $_POST["password"];
		
		$sql = $db->prepare("SELECT * FROM app_users WHERE user_pseudo = :pseudo");
		$sql->execute([":pseudo" => $pseudo]);
		$row = $sql->fetch(PDO::FETCH_OBJ);
		if($row){
			if(password_verify($password, $row->user_password)){
				$results["error"] = false;
				$results["id"] = $row->user_id;
				$results["nom"] = $row->user_nom;
				$results["prenom"] = $row->user_prenom;
				$results["pseudo"] = $row->user_pseudo;
				$results["email"] = $row->user_email;
			}else{
				$results["error"] = true;
				$results["message"] = "Pseudo ou mot de passe incorrect";
			}
		}else{
			$results["error"] = true;
			$results["message"] = "Pseudo ou mot de passe incorrect";
		}
		
	}else{
		$results["error"] = true;
		$results["message"] = "Veuillez remplir tous les champs";
	}
	
	
	echo json_encode($results);
	
}

?>