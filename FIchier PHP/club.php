<?php

$db = new PDO("mysql:host=jasonlagcp1.mysql.db;dbname=jasonlagcp1", "jasonlagcp1", "Jason17240");

if(!empty($_POST)){
	
	if(!empty($_POST["dep"])){
		
		$dep = $_POST["dep"];
		
		$sql = $db->prepare("SELECT club_id, club_nom FROM app_clubs INNER JOIN app_departements ON app_clubs.club_departement=app_departements.dep_id WHERE dep_nom = :dep");
		$sql->execute([":dep" => $dep]);
		$row = $sql->fetchAll(PDO::FETCH_OBJ);
		foreach($row as $ligne){
			$results[$ligne->club_id] = $ligne->club_nom;
		}
	}else{
		$results["error"] = true;
		$results["message"] = "Veuillez remplir tous les champs";
	}
	
	echo json_encode($results);
	
}
	
	

?>