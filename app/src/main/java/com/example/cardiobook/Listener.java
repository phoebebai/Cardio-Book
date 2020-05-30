/**
 * @version: V1.0
 * @author: Yifei Bai
 * @InterfaceName: Listener
 * @packageName:com.example.cardiobook
 * @description: this interface is used to update after user adding editing and deleting measurements.
 * @data: 2019-02-4
 * @designreason: need to update data when user does activity on the measurements.
 **/

package com.example.cardiobook;

public interface Listener {
    public void update();
}
