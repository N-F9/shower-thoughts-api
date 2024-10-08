define((require) => {
	const interceptor = require("rest/interceptor");

	return interceptor({
		request: (request /*, config, meta */) => {
			/* If the URI is a URI Template per RFC 6570 (https://tools.ietf.org/html/rfc6570), trim out the template part */
			if (request.path.indexOf("{") === -1) {
				return request;
			}
			request.path = request.path.split("{")[0];
			return request;
		},
	});
});
