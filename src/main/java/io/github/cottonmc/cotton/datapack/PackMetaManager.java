package io.github.cottonmc.cotton.datapack;

import blue.endless.jankson.Jankson;
import io.github.cottonmc.cotton.Cotton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PackMetaManager {
	public static void saveMeta() {
		File metaFile = new File(Cotton.DATA_PACK_LOCATION, "pack.mcmeta");
		if (metaFile.exists()) return;
		Cotton.logger.info("Generating Cotton data pack");
		Jankson jankson = Jankson.builder().build();
		try {
			String result = jankson
					.toJson(new MetaPackMeta())
					.toJson(false, true, 0);
			if (!Cotton.DATA_PACK_LOCATION.exists()) Cotton.DATA_PACK_LOCATION.mkdirs();
			metaFile.createNewFile();
			FileOutputStream out = new FileOutputStream(metaFile, false);
			out.write(result.getBytes());
			out.flush();
			out.close();

		} catch (IOException e) {
			Cotton.logger.warn("Failed to create pack metadata: "+e);
		}
	}

	private static class MetaPackMeta {
		public PackMeta pack;

		{
			try {
				pack = PackMeta.class.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				Cotton.logger.warn("Failed to create pack metadata: "+e);
			}
		}
	}
}
