<?php

class Rest extends CI_Controller {
    
    public function __construct() {
        parent::__construct();
    }
    
    public function login($username, $password) {
        echo 'sukses '.$username.' '.$password;
    }

}