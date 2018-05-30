<?php

$db = new PDO("mysql:host=jasonlagcp1.mysql.db;dbname=jasonlagcp1;charset=UTF8", "jasonlagcp1", "Jason17240");

			
	$sql = $db->prepare("SELECT * FROM app_departements");
	$sql->execute();
	$row = $sql->fetchAll(PDO::FETCH_OBJ);
	foreach($row as $ligne){
		$results[$ligne->dep_id] = $ligne->dep_nom;
	}
	
	echo json_encode($results);

?>