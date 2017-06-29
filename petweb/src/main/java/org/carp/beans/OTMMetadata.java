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

import org.carp.annotation.CarpAnnotation.Cascade;
import org.carp.annotation.CarpAnnotation.Container;
import org.carp.annotation.CarpAnnotation.Lazy;

public class OTMMetadata extends Metadata implements Cloneable{
	private String fkey;
	private String foreignName;
	private String fieldName;
	private Class<?> childClass;
	private Container contain;
	private Cascade cascade;
	private Lazy	lazy;
	private String remark;
	public String getFkey() {
		return fkey;
	}
	public void setFkey(String fkey) {
		this.fkey = fkey;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Class<?> getChildClass() {
		return childClass;
	}
	public void setChildClass(Class<?> childClass) {
		this.childClass = childClass;
	}
	public Container getContain() {
		return contain;
	}
	public void setContain(Container contain) {
		this.contain = contain;
	}
	public Cascade getCascade() {
		return cascade;
	}
	public void setCascade(Cascade cascade) {
		this.cascade = cascade;
	}
	public Lazy getLazy() {
		return lazy;
	}
	public void setLazy(Lazy lazy) {
		this.lazy = lazy;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getForeignName() {
		return foreignName;
	}
	public void setForeignName(String foreignName) {
		this.foreignName = foreignName;
	}
	
	@Override
	protected OTMMetadata clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (OTMMetadata)super.clone();
	}
}
