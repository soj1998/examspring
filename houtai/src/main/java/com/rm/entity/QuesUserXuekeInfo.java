package com.rm.entity;

public class QuesUserXuekeInfo {
			private QueandAns quesInfo;
			private XueKeBaoCun xuekeinfo;

			public QuesUserXuekeInfo() {

			}

			public QuesUserXuekeInfo(QueandAns quesInfo) {
		  		XueKeBaoCun xuekeinfo = new XueKeBaoCun();
		  		this.quesInfo = quesInfo;
		  		this.xuekeinfo = xuekeinfo;
			}

			public QuesUserXuekeInfo(XueKeBaoCun xuekeinfo) {
				QueandAns quesInfo = new QueandAns();
			    this.quesInfo = quesInfo;
			    this.xuekeinfo = xuekeinfo;
			}
			public QuesUserXuekeInfo(QueandAns quesInfo, XueKeBaoCun xuekeinfo) {
				this.quesInfo = quesInfo;
				this.xuekeinfo = xuekeinfo;
			}

			public QueandAns getQuesInfo() {
				return quesInfo;
			}

			public void setQuesInfo(QueandAns quesInfo) {
				this.quesInfo = quesInfo;
			}

			public XueKeBaoCun getXuekeinfo() {
				return xuekeinfo;
			}

			public void setXuekeinfo(XueKeBaoCun xuekeinfo) {
				this.xuekeinfo = xuekeinfo;
			}
	
}
