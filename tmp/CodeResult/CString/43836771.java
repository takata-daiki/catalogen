package okushama.personal.gen;

import java.io.*;

import net.minecraft.src.*;

public class SchematicBlock extends Block {
	public SchematicBlock(int i, int j) {
		super(i, j, Material.iron);
	}

	
	
	
	
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,int par4, EntityPlayer par5EntityPlayer, int par6, float par7, 	float par8, float par9) {
		// TODO Auto-generated method stub
		return blockActivated(par1World, par2,par3,par4,par5EntityPlayer);
	}






	
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		int methodSeparator = 0;
		int methodNumber = 0;
		String torches[] = new String[2000];
		String location[] = new String[2000];
		int torchesnum = 0;
		int locationsum = 0;

		Writer output = null;
		try {
			File file = new File("C:/Users/Admin/Desktop/","SchematicBlockOutput.txt");
			if(!file.exists()){
				file.createNewFile();
			}
			output = new BufferedWriter(new FileWriter(file));
			String p1 = "world.setBlockWithNotify(x+";
			String p2 = ",y+";
			String p3 = ",z+";
			String p4 = ",";
			String p5 = ");";
			String p6 = "world.setBlockAndMetadataWithNotify(x+";
			String newLine = System.getProperty("line.separator");

			String methodPrefix = "public void generate";
			String methodNumberPlace = "(int x, int y, int z){";
			String methodSuffix = "}";

			for (int a = 0; a < 70; a++) {
				for (int b = 0; b < 50; b++) {
					for (int c = 0; c < 50; c++) {

						Integer idGet = world.getBlockId(i + a, j + b, k + c);
						Integer idMeta = world.getBlockMetadata(i + a, j + b, k
								+ c);
						if (idGet != 0) {

							if (idGet == 80 || idGet == 79 || idGet == 19
									|| idGet == 16 || idGet == 56
									|| idGet == 21) {
								String aString = Integer.toString(a);
								String bString = Integer.toString(b);
								String cString = Integer.toString(c);
								String idString = Integer.toString(idGet);
								location[locationsum] = new StringBuilder(p1
										+ aString + p2 + bString + p3 + cString
										+ p4 + idString + p5).toString();
								locationsum++;
							}

							if (idGet == 50 || idGet == 60 || idGet == 59
									|| idGet == 64 || idGet == 68
									|| idGet == 71 || idGet == 96
									|| idGet == 77 || idGet == 65) {
								String aString = Integer.toString(a);
								String bString = Integer.toString(b);
								String cString = Integer.toString(c);
								String idString = Integer.toString(idGet);
								String metaString = Integer.toString(idMeta);
								torches[torchesnum] = new StringBuilder(p6
										+ aString + p2 + bString + p3 + cString
										+ p4 + idString + p4 + metaString + p5)
										.toString();
								torchesnum++;
							}

							if (methodSeparator % 1800 == 0) {
								String methodString = "";
								output.write(methodPrefix);
								output.write(methodString);
								output.write(methodNumberPlace);
								output.write(newLine);
								methodNumber++;
							}
							if (idMeta == 0 && idGet != 50 && idGet != 60
									&& idGet != 59 && idGet != 64
									&& idGet != 68 && idGet != 71
									&& idGet != 96 && idGet != 77
									&& idGet != 80 && idGet != 79
									&& idGet != 19 && idGet != 16
									&& idGet != 56 && idGet != 21
									&& idGet != 65) {
								String aString = Integer.toString(a);
								String bString = Integer.toString(b);
								String cString = Integer.toString(c);
								String idString = Integer.toString(idGet);
								output.write(p1);
								output.write(aString);
								output.write(p2);
								output.write(bString);
								output.write(p3);
								output.write(cString);
								output.write(p4);
								output.write(idString);
								output.write(p5);
								output.write(newLine);
							} else if (idGet != 50 && idGet != 60
									&& idGet != 59 && idGet != 64
									&& idGet != 68 && idGet != 71
									&& idGet != 96 && idGet != 77
									&& idGet != 80 && idGet != 79
									&& idGet != 19 && idGet != 16
									&& idGet != 56 && idGet != 21
									&& idGet != 65) {
								String aString = Integer.toString(a);
								String bString = Integer.toString(b);
								String cString = Integer.toString(c);
								String idString = Integer.toString(idGet);
								String metaString = Integer.toString(idMeta);
								output.write(p6);
								output.write(aString);
								output.write(p2);
								output.write(bString);
								output.write(p3);
								output.write(cString);
								output.write(p4);
								output.write(idString);
								output.write(p4);
								output.write(metaString);
								output.write(p5);
								output.write(newLine);
							}
							methodSeparator++;
						}
					}
				}
			}

			for (int x = 0; x < 2000; x++) {
				if (torches[x] != null) {
					output.write(torches[x]);
					output.write(newLine);
				}
			}
			output.write(newLine);
			output.write(newLine);
			for (int x = 0; x < 100; x++) {
				if (location[x] != null) {
					output.write(location[x]);
					output.write(newLine);
				}
			}
			output.write("}");
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (output != null) {
				output.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Block Read Written");
		entityplayer.addChatMessage("Successfully read blocks!");

		return true;

	}
}