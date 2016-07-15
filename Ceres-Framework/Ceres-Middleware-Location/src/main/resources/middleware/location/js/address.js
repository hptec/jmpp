define([], function() {
	return {
		fromObject : function(obj) {
			obj = obj == undefined ? {} : obj;

			obj.isEmpty = function() {
				if (this.province == undefined || this.city == undefined || this.county == undefined || this.coordinate == undefined) {
					return false;
				}
				return true;
			}

			obj.toString = function() {
				var str = this.province == undefined ? "" : this.province.name == undefined ? "" : this.province.name;
				str = str + (this.city == undefined ? "" : this.city.name == undefined ? "" : this.city.name);
				str = str + (this.county == undefined ? "" : this.county.name == undefined ? "" : this.county.name);
				str = str + (this.street == undefined ? "" : this.street);
				str = str + (this.streetNumber == undefined ? "" : this.streetNumber);
				return str;
			}

			return obj;
		}
	}
});