/*
 * Copyright (C) 2013 Glyptodon LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.sourceforge.guacamole.net.auth.mysql;

import com.google.inject.Inject;
import java.util.Collections;
import java.util.Set;
import net.sourceforge.guacamole.net.auth.mysql.service.ConnectionService;
import org.glyptodon.guacamole.GuacamoleException;
import org.glyptodon.guacamole.GuacamoleSecurityException;
import org.glyptodon.guacamole.net.GuacamoleSocket;
import org.glyptodon.guacamole.net.auth.ConnectionGroup;
import org.glyptodon.guacamole.protocol.GuacamoleClientInformation;

/**
 * The root connection group, here represented as its own dedicated object as
 * the database does not contain an actual root group.
 *
 * @author Michael Jumper
 */
public class MySQLRootConnectionGroup implements ConnectionGroup {

    /**
     * The user this group belongs to. Access is based on his/her permission
     * settings.
     */
    private AuthenticatedUser currentUser;

    /**
     * Service for managing connection objects.
     */
    @Inject
    private ConnectionService connectionService;
    
    /**
     * Creates a new, empty MySQLRootConnectionGroup.
     */
    public MySQLRootConnectionGroup() {
    }

    /**
     * Initializes this root connection group, associating it with the current
     * authenticated user.
     *
     * @param currentUser
     *     The user that created or retrieved this object.
     */
    public void init(AuthenticatedUser currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public String getName() {
        return MySQLConstants.CONNECTION_GROUP_ROOT_IDENTIFIER;
    }

    @Override
    public void setName(String name) {
        throw new UnsupportedOperationException("The root connection group cannot be modified.");
    }

    @Override
    public String getParentIdentifier() {
        return null;
    }

    @Override
    public void setParentIdentifier(String parentIdentifier) {
        throw new UnsupportedOperationException("The root connection group cannot be modified.");
    }

    @Override
    public Type getType() {
        return ConnectionGroup.Type.ORGANIZATIONAL;
    }

    @Override
    public void setType(Type type) {
        throw new UnsupportedOperationException("The root connection group cannot be modified.");
    }

    @Override
    public Set<String> getConnectionIdentifiers() throws GuacamoleException {
        return connectionService.getRootIdentifiers(currentUser);
    }

    @Override
    public Set<String> getConnectionGroupIdentifiers()
            throws GuacamoleException {
        /* STUB */
        return Collections.EMPTY_SET;
    }

    @Override
    public String getIdentifier() {
        return MySQLConstants.CONNECTION_GROUP_ROOT_IDENTIFIER;
    }

    @Override
    public void setIdentifier(String identifier) {
        throw new UnsupportedOperationException("The root connection group cannot be modified.");
    }

    @Override
    public GuacamoleSocket connect(GuacamoleClientInformation info)
            throws GuacamoleException {
        throw new GuacamoleSecurityException("Permission denied.");
    }

    @Override
    public int getActiveConnections() {
        return 0;
    }

}
