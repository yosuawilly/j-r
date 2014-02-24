<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of My_Util
 *
 * @author LenovoDwidasa
 */
class My_Util {
    
    public function __construct() {
        
    }
    
    public static function isArrayDifferent($array = array()) {
        if(count($array)==0) return TRUE;
        
        $end = count($array);
        for($j=0; $j<$end; $j++) :
            for ($i=$j; $i<$end; $i++) :
                if (($i+1) < $end)
                if ($array[$j] == $array[$i+1]) {
                    return FALSE;
                }
            endfor;
        endfor;
        
        return TRUE;
    }
    
}