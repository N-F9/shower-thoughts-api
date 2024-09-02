define(() => {
	/* Convert a single or array of resources into "URI1\nURI2\nURI3..." */
	return {
		read: (str /*, opts */) => str.split("\n"),
		write: (obj /*, opts */) => {
			// If this is an Array, extract the self URI and then join using a newline
			if (Array.isArray(obj)) {
				return obj.map((resource) => resource._links.self.href).join("\n");
			}

			return obj._links.self.href;
		},
	};
});
