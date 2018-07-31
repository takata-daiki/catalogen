/**
 * NOTICE --- T20120415
 * 
 * COPYRIGHT (C) ERIC FENG, HTTP://WWW.SINCE10.COM, ALL RIGHTS RESERVED.
 * 
 * PERMISSION IS HEREBY GRANTED, FREE OF CHARGE, TO ANY PERSON OBTAINING
 * A COPY OF THIS SOFTWARE AND ASSOCIATED DOCUMENTATION FILES (THE
 * "SOFTWARE"), TO DEAL IN THE SOFTWARE WITHOUT RESTRICTION, INCLUDING
 * WITHOUT LIMITATION THE RIGHTS TO USE, COPY, MODIFY, MERGE, PUBLISH,
 * DISTRIBUTE, SUBLICENSE, AND/OR SELL COPIES OF THE SOFTWARE, AND TO
 * PERMIT PERSONS TO WHOM THE SOFTWARE IS FURNISHED TO DO SO, SUBJECT TO
 * THE FOLLOWING CONDITIONS:
 * 
 * THE ABOVE COPYRIGHT NOTICE AND THIS PERMISSION NOTICE SHALL BE
 * INCLUDED IN ALL COPIES OR SUBSTANTIAL PORTIONS OF THE SOFTWARE.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package xeno.persist.criteria;

enum ConditionType {

	/**
	 * <p>
	 * The "AND" condition.
	 * </p>
	 */
	And,

	/**
	 * <p>
	 * The "OR" condition.
	 * </p>
	 */
	Or,

	/**
	 * <p>
	 * The "=" condition.
	 * </p>
	 */
	EqualTo,

	/**
	 * <p>
	 * The "<>" condition.
	 * </p>
	 */
	NotEqualTo,

	/**
	 * <p>
	 * The ">" condition.
	 * </p>
	 */
	GreaterThan,

	/**
	 * <p>
	 * The ">=" condition.
	 * </p>
	 */
	GreaterThanOrEqualTo,

	/**
	 * <p>
	 * The "<" condition.
	 * </p>
	 */
	LessThan,

	/**
	 * <p>
	 * The "<=" condition.
	 * </p>
	 */
	LessThanOrEqualTo,

	/**
	 * <p>
	 * The "BETWEEN" condition.
	 * </p>
	 */
	Between,

	/**
	 * <p>
	 * The "NOT BETWEEN" condition.
	 * </p>
	 */
	NotBetween,

	/**
	 * <p>
	 * The "IN" condition.
	 * </p>
	 */
	In,

	/**
	 * <p>
	 * The "NOT IN" condition.
	 * </p>
	 */
	NotIn,

	/**
	 * <p>
	 * The "LIKE" condition.
	 * </p>
	 */
	Like,

	/**
	 * <p>
	 * The "NOT LIKE" condition.
	 * </p>
	 */
	NotLike;
}
