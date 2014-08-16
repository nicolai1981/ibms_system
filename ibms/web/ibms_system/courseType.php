<?php
include 'util.php';
initDB();

$in_token = ($_POST['token']);
if (!checkToken($in_token)) {
	echo "{\"CODE\":1}";
}
echo "{\"CODE\":100}";

$in_cmd = ($_POST['CMD']);
if ($in_cmd == 1) {
	echo "{\"CODE\":100}";
} elseif ($in_cmd == 2) {
	echo "{\"CODE\":200}";
} elseif ($in_cmd == 3) {
	$in_version = ($_POST['VERSION']);
	$sqlQuery = "SELECT TIPO_CURSO.* FROM TIPO_CURSO ORDER BY _ID";
	$resultSelect = mysql_query($sqlQuery);
	$resultArray = mysql_fetch_array($resultSelect);
	while ($resultArray) {
		$course_id = $resultArray["_ID"];
		$course_name = $resultArray["VERSAO"];
		$course_version = $resultArray["NOME"];
		$course_start_date = $resultArray["DATA_INICIAL"];
		$course_end_date = $resultArray["DATA_FINAL"];
		$result = $result
			."{"
			."\"ID\":".$course_id.","
			."\"VR\":".$course_version.","
			."\"NM\":"."\"".$course_name."\","
			."\"SD\":"."\"".$course_start_date."\","
			."\"ED\":"."\"".$course_end_date."\""
			."}";
		$resultArray = mysql_fetch_array($resultSelect);
		if ($resultArray) {
			$result = $result.",";
		}
	}
	echo "{\"CODE\":0,\"LIST\":[".$result."]}";
} else {
	echo "{\"CODE\":300}";
}
?>
