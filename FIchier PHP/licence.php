<?php

$db = new PDO("mysql:host=jasonlagcp1.mysql.db;dbname=jasonlagcp1", "jasonlagcp1", "Jason17240");
$results["error"] = false;
$results["message"] = [];

if(!empty($_POST)){
	
	if(!empty($_POST["id"])){
		
		$id = $_POST["id"];
		
		$sql = $db->prepare("SELECT * FROM app_licences WHERE lice_id_user = :id");
		$sql->execute([":id" => $id]);
		$row = $sql->fetch(PDO::FETCH_OBJ);
		if($row){
			$results["error"] = false;
			$results["licence"] = true;
			$results["lice_id"] = $row->lice_id;
			$results["lice_id_user"] = $row->lice_id_user;
			$results["lice_id_club"] = $row->lice_id_club;
			$results["lice_date_create"] = $row->lice_date_create;
			
			$sql2 = $db->prepare("SELECT * FROM app_clubs INNER JOIN app_departements ON app_clubs.club_departement=app_departements.dep_id WHERE club_id = :club_id");
			$sql2->execute([":club_id" => $row->lice_id_club]);
			$row2 = $sql2->fetch(PDO::FETCH_OBJ);
			if($row2){
				$results["club_id"] = $row2->club_id;
				$results["club_nom"] = $row2->club_nom;
				$results["club_ville"] = $row2->club_ville;
				$results["club_departement"] = $row2->club_departement;
				$results["dep_id"] = $row2->dep_id;
				$results["dep_nom"] = $row2->dep_nom;
			}
		}else{
			$results["error"] = false;
			$results["licence"] = false;
		}
	}else{
		$results["error"] = true;
		$results["message"] = "Veuillez remplir tous les champs";
	}
	
	echo json_encode($results);
	
}
	
	

?>