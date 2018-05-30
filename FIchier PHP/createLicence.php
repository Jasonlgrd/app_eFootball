<?php

$db = new PDO("mysql:host=jasonlagcp1.mysql.db;dbname=jasonlagcp1", "jasonlagcp1", "Jason17240");
$results = "";

if(!empty($_POST)){
	
	if(!empty($_POST["user_id"]) && !empty($_POST["club_nom"])){
		
		$user_id = $_POST["user_id"];
		$club_nom = $_POST["club_nom"];
		$date = date("Y-m-d H:i:s");
		
		$sql = $db->prepare("SELECT * FROM app_clubs WHERE club_nom = :club_nom");
		$sql->execute([":club_nom" => $club_nom]);
		$row = $sql->fetch(PDO::FETCH_OBJ);
		if($row){
			$club_id = $row->club_id;
			$sql2 = $db->prepare("INSERT INTO app_licences (lice_id_user, lice_id_club, lice_date_create) VALUES (:user_id, :club_id, :date)");
			$sql2->execute([":user_id" => $user_id, ":club_id" => $club_id, ":date" => $date]);
			$results = "Licence créée";
		}else{
			$results = "Aucune ligne dans la base de données";
		}
	}else{
		$results = "Veuillez remplir tous les champs";
	}
	
	echo ($results);
	
}
	
	

?>