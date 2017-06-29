/**
 * Copyright 2009-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.carp.beans;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.carp.annotation.CarpAnnotation;
import org.carp.annotation.Column;
import org.carp.exception.CarpException;

public class ColumnsProcessor  implements AnnotationProcessor{
	private static final Logger logger = Logger.getLogger(ColumnsProcessor.class);
	public void parse(CarpBean bean, Class<?> cls)  throws CarpException{
		TableMetadata table = bean.getTableInfo();
		Field[] fields = cls.getDeclaredFields();
		for(Field field:fields){
			Annotation[] annos = field.getAnnotations();
			for(Annotation anno:annos){
				if(anno instanceof Column){
					ColumnsMetadata cm = new ColumnsMetadata();
					table.addColumnsMetadata(cm);
					Column ca = (Column)anno;
					cm.setColName(ca.name().toUpperCase());
					cm.setFieldName(field.getName());
					cm.setFieldType(field.getType());
					cm.setLength(ca.length());
					cm.setNull(ca.isNull()==CarpAnnotation.Nullable.Yes?true:false);
					cm.setPrecision(ca.precision());
					cm.setRemark(ca.remark());
					cm.setField(field);
					if(logger.isDebugEnabled())
						logger.debug("Table Column, column:"+cm.getColName()+
								", field:"+cm.getFieldName()+
								", lenght:"+cm.getLength());
				}
			}
		}
	}
}
