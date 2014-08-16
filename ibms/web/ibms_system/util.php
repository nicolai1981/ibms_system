<?php
function initDB() {
    $banco = "rjvalentes_1";
    $usuario = "rjvalentes_1";
    $senha = "960751x0";
    $hostname = "dbmy0062.whservidor.com";
    $conn = mysql_connect($hostname,$usuario,$senha);
    mysql_select_db($banco) or die("N&atilde;o foi poss&iacute;vel conectar ao banco MySQL");
    if (!$conn) {
      exit;
    }
}

function checkToken($token) {
	return true;
}



































function getUserName($id) {
	$userName = "Usu&aacute;rio Inv&aacute;lido";
	if ($id != "") {
		$resultSelect = mysql_query("SELECT MEM_NAME FROM MEMBER WHERE MEM_ID=".$id);
		if ($resultArray = mysql_fetch_array($resultSelect)) {
			$userName = $resultArray["MEM_NAME"];
		}
	}
	return $userName;
}

function getLeaderAndGenerationList($set_liderId) {
	$result = "<option selected value=\"-1\">NENHUM</option>";
	$resultSelect = mysql_query("SELECT MEMBER.* FROM MEMBER WHERE MEM_IS_LIDER=2 ORDER BY MEM_NAME");
	while ($resultArray = mysql_fetch_array($resultSelect)) {
		$user_id = $resultArray["MEM_ID"];
		$genSelect = mysql_query("SELECT GENERATION.* FROM GENERATION WHERE GEN_ID=".$resultArray["MEM_GEN_ID"]);
		if ($genArray = mysql_fetch_array($genSelect)) {
			$gen_name = " - ".$genArray["GEN_NAME"];
		} else {
			$gen_name = " - Indefinido";
		}

		if ($resultArray["MEM_STATUS"] == 1) {
			$color = "green";
			$status = "";
		} else {
			$color = "red";
			$status = "(desativado)";
		}

		$result = $result."<option value=\"".$user_id."\"";
		if ($set_liderId == $user_id) {
			$result = $result." selected=\"true\"";
		}
		$result = $result." style=\"color: ".$color.";\">".$status.$resultArray["MEM_NAME"].$gen_name."</font></option>";
	}
	return $result;
}

function getDistrictList($set_districtId) {
	$result = "<option selected value=\"-1\">NENHUM</option>";
	$sqlQuery = "SELECT DISTRICT.* FROM DISTRICT ORDER BY DTR_NAME";
	$resultSelect = mysql_query($sqlQuery);
	while ($resultArray = mysql_fetch_array($resultSelect)) {
		$dtr_id = $resultArray["DTR_ID"];
		$result = $result."<option value=\"".$dtr_id."\"";
		if ($set_districtId == $dtr_id) {
			$result = $result." selected=\"true\"";
		}
		$result = $result.">".$resultArray["DTR_NAME"]." - ".$resultArray["DTR_CITY"]."</option>";
	}
	return $result;
}

function verifyDate($date, $isEmptyOK) {
	$message = "OK";

	if ($isEmptyOK && ((strlen($date) == 0) || ($date == "DD/MM/AAAA"))) {
	} else if (!$isEmptyOK && ((strlen($date) == 0) || ($date == "DD/MM/AAAA"))) {
		$message = " Preencha a data";
	} else if ((strlen($date) < 10)
					|| ($date[2] != '/')
					|| ($date[5] != '/')
					|| !is_numeric (substr ($date, 0, 2))
					|| !is_numeric (substr ($date, 3, 2))
					|| !is_numeric (substr ($date, 6))) {
			$message = " O formato da data deve ser DD/MM/AAAA";
	} else {
		$day = substr ($date, 0, 2);
		$month = substr ($date, 3, 2);
		$year = substr ($date, 6);
		if (!checkdate($month, $day, $year)) {
			$message = " Data inexistente";
		}
	}
	return $message;
}

function getCellAndLeaderList($set_cellId) {
	$result = "<option selected value=\"-1\">NENHUM</option>";
	$resultSelect = mysql_query("SELECT CELL.*, MEMBER.* FROM CELL, MEMBER WHERE CEL_LEADER_ID=MEM_ID ORDER BY MEM_NAME, CEL_TIME");
	while ($resultArray = mysql_fetch_array($resultSelect)) {
		$cel_id = $resultArray["CEL_ID"];
		$cel_date = $resultArray["CEL_TIME"];
		$cel_day_of_week = substr ($cel_date, 9, 1);
		switch ($cel_day_of_week) {
		case 1:
			$cel_day_of_week = "Domingo";
			break;
		case 2:
			$cel_day_of_week = "Segunda";
			break;
		case 3:
			$cel_day_of_week = "Ter&ccedil;a";
			break;
		case 4:
			$cel_day_of_week = "Quarta";
			break;
		case 5:
			$cel_day_of_week = "Quinta";
			break;
		case 6:
			$cel_day_of_week = "Sexta";
			break;
		case 7:
			$cel_day_of_week = "S&aacute;bado";
			break;
		}
		$cel_time = substr ($cel_date, 11, 5);
		$cel_time = $cel_day_of_week." - ".$cel_time;

		if ($resultArray["CEL_STATUS"] == 1) {
			$color = "green";
			$status = "";
		} else {
			$color = "red";
			$status = "(desativado)";
		}

		$result = $result."<option value=\"".$cel_id."\"";
		if ($set_cellId == $cel_id) {
			$result = $result." selected=\"true\"";
		}
		$result = $result." style=\"color: ".$color.";\">".$status.$resultArray["MEM_NAME"]." - ".$cel_time."</font></option>";
	}
	return $result;
}

function verifyTime($date, $isEmptyOK) {
	$message = "OK";

	if ($isEmptyOK && ((strlen($date) == 0) || ($date == "HH:MM"))) {
	} else if (!$isEmptyOK && ((strlen($date) == 0) || ($date == "HH:MM"))) {
		$message = " Preencha a hora";
	} else if ((strlen($date) < 5)
					|| ($date[2] != ':')
					|| !is_numeric (substr ($date, 0, 2))
					|| !is_numeric (substr ($date, 3, 2))) {
			$message = " O formato da data deve ser HH:MM";
	} else {
		$hour = intval(substr ($date, 0, 2));
		$minutes = intval(substr ($date, 3, 2));
		if (($hour > 23) || (($minutes > 59))) {
			$message = " Hora inexistente";
		}
	}
	return $message;
}
?>