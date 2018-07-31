/**
 * A library to interact with Virtual Worlds such as OpenSim
 * Copyright (C) 2012  Jitendra Chauhan, Email: jitendra.chauhan@gmail.com
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License,
 * or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.ngt.jopenmetaverse.tc.commands.movement;

import com.ngt.jopenmetaverse.shared.types.UUID;
import com.ngt.jopenmetaverse.shared.util.Utils;
import com.ngt.jopenmetaverse.tc.TestClient;
import com.ngt.jopenmetaverse.tc.commands.Command;
import com.ngt.jopenmetaverse.tc.commands.CommandCategory;

public class MovetoCommand extends Command
{
    public MovetoCommand(TestClient client)
    {
    	super(client);
        Name = "moveto";
        Description = "Moves the avatar to the specified global position using simulator autopilot. Usage: moveto x y z";
        Category = CommandCategory.Movement;
    }

    @Override
		public String Execute(String[] args, UUID fromAgentID)
    {
        if (args.length != 3)
            return "Usage: moveto x y z";

        long[] regionXY = new long[2];
        Utils.longToUInts(Client.network.getCurrentSim().Handle.longValue(), regionXY);

        double[] x = new double[1];
        double[] y = new double[1]; 
        double[] z = new double[1];
        if (!Utils.tryParseDouble(args[0], x) ||
            !Utils.tryParseDouble(args[1], y) ||
            !Utils.tryParseDouble(args[2], z))
        {
            return "Usage: moveto x y z";
        }

        // Convert the local coordinates to global ones by adding the region handle parts to x and y
        x[0] += (double)regionXY[0];
        y[0] += (double)regionXY[1];

        Client.self.AutoPilot(x[0], y[0], z[0]);

        return String.format("Attempting to move to <%s,%s,%s>", x[0], y[0], z[0]);
    }
}