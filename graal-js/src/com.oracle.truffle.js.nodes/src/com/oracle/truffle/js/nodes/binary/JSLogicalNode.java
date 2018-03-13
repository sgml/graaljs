/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;

public abstract class JSLogicalNode extends JSBinaryNode {
    @Child private JSToBooleanNode toBooleanCast;

    protected final ConditionProfile canShortCircuit = ConditionProfile.createBinaryProfile();

    public JSLogicalNode(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    protected boolean toBoolean(Object operand) {
        if (toBooleanCast == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            toBooleanCast = insert(JSToBooleanNode.create());
        }
        return toBooleanCast.executeBoolean(operand);
    }
}
