package com.iamnige.hadoop.conf;

import com.iamnige.hadoop.hdfs.DfsConf;

public class HadoopConf implements DfsConf{
	public  String hdfsLocation = "hdfs://192.168.1.11:9000";

	@Override
	public String getLocation() {
		return this.hdfsLocation;
	}
}
