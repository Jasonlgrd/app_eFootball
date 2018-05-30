<?php

$db = new PDO("mysql:host=jasonlagcp1.mysql.db;dbname=jasonlagcp1", "jasonlagcp1", "Jason17240");
$results["error"] = false;
$results["message"] = [];

if(isset($_POST)){
	
	if(!empty($_POST["nom"]) && !empty($_POST["prenom"]) && !empty($_POST["pseudo"]) && !empty($_POST["email"]) && !empty($_POST["password"]) && !empty($_POST["password2"])){
		
		$nom = $_POST["nom"];
		$prenom = $_POST["prenom"];
		$pseudo = $_POST["pseudo"];
		$email = $_POST["email"];
		$password = $_POST["password"];
		$password2 = $_POST["password2"];
		
		//Vérification du nom
		if(strlen($nom) < 2 || !preg_match("/^[a-zA-Z ]+$/", $nom) || strlen($nom) > 100){
			$results["error"] = true;
			$results["message"]["nom"] = "Nom invalide";
		}
		
		//Vérification du prénom
		if(strlen($prenom) < 2 || !preg_match("/^[a-zA-Z ]+$/", $prenom) || strlen($prenom) > 100){
			$results["error"] = true;
			$results["message"]["prenom"] = "Prénom invalide";
		}
		
		//Vérification du pseudo
		if(strlen($pseudo) < 2 || !preg_match("/^[a-zA-Z0-9 _-]+$/", $pseudo) || strlen($pseudo) > 100){
			$results["error"] = true;
			$results["message"]["pseudo"] = "Pseudo invalide";
		}else{
			//Vérifier que le pseudo n'éxiste pas
			$requete = $db->prepare("SELECT user_id FROM app_users WHERE user_pseudo = :pseudo");
			$requete->execute([":pseudo" => $pseudo]);
			$row = $requete->fetch();
			if($row){
				$results["error"] = true;
				$results["message"]["pseudo"] = "Le pseudo est déjà pris";
			}
		}
		
		//Vérification de l'email
		if(!filter_var($email, FILTER_VALIDATE_EMAIL)){
			$results["error"] = true;
			$results["message"]["email"] = "Email invalide";
		}else{
			//Vérifier que l'email n'éxiste pas
			$requete = $db->prepare("SELECT user_id FROM app_users WHERE user_email = :email");
			$requete->execute([":email" => $email]);
			$row = $requete->fetch();
			if($row){
				$results["error"] = true;
				$results["message"]["email"] = "L'email existe déjà";
			}
		}
		
		//Vérification des passwords
		if($password !== $password2){
			$results["error"] = true;
			$results["message"]["password"] = "Les mots de passes doivent être identiques";
		}
		
		//Insertion des données
		if($results["error"] === false){
			
			$password = password_hash($password, PASSWORD_BCRYPT);
			
			//Insertion
			$sql = $db->prepare("INSERT INTO app_users(user_nom, user_prenom, user_pseudo, user_email, user_password) VALUES(:nom, :prenom, :pseudo, :email, :password)");
			$sql->execute([":nom" => $nom, ":prenom" => $prenom, ":pseudo" => $pseudo, ":email" => $email, ":password" => $password]);
			
			if(!$sql){
				$results["error"] = true;
				$results["message"] = "Erreur lors de l'inscription";
			}
		}
		
	}else{
		$results["error"] = true;
		$results["message"] = "Veuillez remplir tous les champs";
	}
	
	echo json_encode($results);
	
}

?>