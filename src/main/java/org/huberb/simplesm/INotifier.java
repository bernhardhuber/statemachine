/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.simplesm;

import java.io.Serializable;

/**
 *
 * @author berni
 */
@FunctionalInterface
public interface INotifier extends Serializable {

    void notify(NotificationEvent notificationEvent);
}
