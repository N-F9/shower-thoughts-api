const React = require('react')
const ReactDOM = require('react-dom')
const client = require('./client')

class App extends React.Component {
	constructor(props) {
		super(props)
		this.state = {thought: {}}
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/thoughts/random'}).done(response => {
			console.log(response)
			this.setState({thought: response.entity._embedded})
		});
	}

	render() {
		return (
			<>
				<p>{this.state.thought}</p>
			</>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)
