<?php

try {
    $db = new PDO('mysql:dbname=testdb;host=db', 'testuser', 'testpass');
    echo "接続に成功しました";
} catch(PDOException $e) {
    echo "接続に失敗しました";
    echo $e->getMessage();
    exit;
}

?>