<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Upload
 *
 * @author USER
 */
class Upload {

    public function __construct() {
        $this->CI =& get_instance();
        $this->CI->load->library('session');
        $this->CI->load->helper('cookie');
    }

    function nicupload_error($msg) {
        echo $this->nicupload_output(array('error' => $msg));
    }

    function nicupload_output($status, $showLoadingMsg = false) {
        $script = json_encode($status);
        $script = str_replace("\\/", '/', $script);
        echo $script;

        exit;
    }

    function ini_max_upload_size() {
        $post_size = ini_get('post_max_size');
        $upload_size = ini_get('upload_max_filesize');
        if(!$post_size) $post_size = '8M';
        if(!$upload_size) $upload_size = '2M';

        return min($this->ini_bytes_from_string($post_size), $this->ini_bytes_from_string($upload_size) );
    }

    function ini_bytes_from_string($val) {
        $val = trim($val);
        $last = strtolower($val[strlen($val)-1]);
        switch($last) {
            // The 'G' modifier is available since PHP 5.1.0
            case 'g':
                $val *= 1024;
            case 'm':
                $val *= 1024;
            case 'k':
                $val *= 1024;
        }
        return $val;
    }

    function bytes_to_readable( $bytes ) {
        if ($bytes<=0)
            return '0 Byte';

        $convention=1000; //[1000->10^x|1024->2^x]
        $s=array('B', 'kB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB');
        $e=floor(log($bytes,$convention));
        return round($bytes/pow($convention,$e),2).' '.$s[$e];
    }

}
